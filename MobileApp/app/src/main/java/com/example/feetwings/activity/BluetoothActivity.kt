package com.example.feetwings.activity

import android.Manifest
import android.app.Activity
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.feetwings.R
import com.example.feetwings.ScanResultAdapter
import kotlinx.android.synthetic.main.activity_bluetooth.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.alert
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

private lateinit var gble: BluetoothGatt
var flag: Boolean = false


class BluetoothActivity : AppCompatActivity() {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 2
        private const val ENABLE_BLUETOOTH_REQUEST_CODE = 1
        private const val GATT_MAX_MTU_SIZE = 517
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        btn_bluetooth_scan.setOnClickListener {
            scan_results_recycler_view.visibility = View.VISIBLE
            if (isScanning) {
                stopBleScan()
            } else {
                startBleScan()
            }
        }

        dev.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setupRecyclerView()

    }

    // sets up view to show scan results
    private fun setupRecyclerView() {
        scan_results_recycler_view.apply {
            adapter = scanResultAdapter
            layoutManager = LinearLayoutManager(
                this@BluetoothActivity,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = scan_results_recycler_view.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }


    // sets the text on scan button
    private var isScanning = false
        set(value) {
            field = value
            runOnUiThread { btn_bluetooth_scan.text = if (value) "Stop Scan" else "Start Scan" }
        }

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    //declares a scanner
    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }
    private var scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private fun startBleScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isLocationPermissionGranted) {
            requestLocationPermission()
        } else {
            scanResults.clear()
            scanResultAdapter.notifyDataSetChanged()
            bleScanner.startScan(null, scanSettings, scanCallback)
            isScanning = true
        }
    }

    //
    private val scanResults = mutableListOf<ScanResult>()
    private val scanResultAdapter: ScanResultAdapter by lazy {
        ScanResultAdapter(scanResults) { result ->
            connection_status.text = "Connecting"
            if (isScanning) {
                stopBleScan()
            }

            with(result.device) {
                Log.w("ScanResultAdapter", "Connecting to $address")
                connectGatt(this@BluetoothActivity, false, callback)
            }
        }
    }


    val callback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {

            val deviceAddress = gatt.device.address

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.w("BluetoothGattCallback", "Successfully connected to $deviceAddress")

                    Handler(Looper.getMainLooper()).post {
                        gatt.discoverServices()
                    }


                }

            }
        }




        override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
            Timber.w("ATT MTU changed to $mtu, success: ${status == BluetoothGatt.GATT_SUCCESS}")
            gatt.requestMtu(GATT_MAX_MTU_SIZE)
        }
        fun BluetoothGattCharacteristic.isReadable(): Boolean =
            containsProperty(BluetoothGattCharacteristic.PROPERTY_READ)


        fun BluetoothGattCharacteristic.containsProperty(property: Int): Boolean {
            return properties and property != 0
        }



        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ){

            with(characteristic) {
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        /* Log.i(
                                 "BluetoothGattCallback",
                                 "Read characteristic $uuid:\n${value.first().toInt()}"
                         )*/
                        flag = false
                    }
                    BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                        Log.e("BluetoothGattCallback", "Read not permitted for $uuid!")
                    }
                    else -> {
                        Log.e(
                            "BluetoothGattCallback",
                            "Characteristic read failed for $uuid, error: $status"
                        )
                    }
                }
            }


            //val intent = Intent(baseContext, MainActivity::class.java)
            //startActivity(intent)


        }

        // ... somewhere outside BluetoothGattCallback

        private fun BluetoothGatt.printGattTable() {
            if (services.isEmpty()) {
                Log.i("printGattTable", "No service and characteristic available, call discoverServices() first?")
                return
            }
            services.forEach { service ->
                val characteristicsTable = service.characteristics.joinToString(
                    separator = "\n|--",
                    prefix = "|--"
                ) { it.uuid.toString() }
                Log.i("printGattTable", "\nService ${service.uuid}\nCharacteristics:\n$characteristicsTable"
                )
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            with(gatt) {
                Log.w(
                    "BluetoothGattCallback",
                    "Discovered ${services.size} services for ${device.address}"
                )
                gble = gatt
                connection_status.text = "Connected"
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                printGattTable()
            }
        }
    }


    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
            if (indexQuery != -1) { // A scan result already exists with the same address
                scanResults[indexQuery] = result
                scanResultAdapter.notifyItemChanged(indexQuery)
            } else {
                with(result.device) {
                    //Log.i(“ScanCallback”, "Found BLE device! Name: ${name ?: "Unnamed"}, address: $address")
                }
                scanResults.add(result)
                scanResultAdapter.notifyItemInserted(scanResults.size - 1)
            }
        }

        override fun onScanFailed(errorCode: Int) {
//            Log.e(“ScanCallback”, "onScanFailed: code $errorCode")
        }
    }

    private fun stopBleScan() {
        bleScanner.stopScan(scanCallback)
        isScanning = false
    }


    // From the previous section:
    override fun onResume() {
        super.onResume()
        if (!bluetoothAdapter.isEnabled) {
            promptEnableBluetooth()
        }
    }

    private fun promptEnableBluetooth() {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST_CODE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ENABLE_BLUETOOTH_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) {
                    Toast.makeText(
                        this,
                        "Please go to settings to turn on Bluetooth",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private val isLocationPermissionGranted
        get() = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    private fun Context.hasPermission(permissionType: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permissionType) ==
                PackageManager.PERMISSION_GRANTED
    }

    /**
     * function to request the permission of Location.
     */
    private fun requestLocationPermission() {
        if (isLocationPermissionGranted) {
            return
        }
        runOnUiThread {
            alert {
                title = "Location permission required"
                message = "Starting from Android M (6.0), the system requires apps to be granted " +
                        "location access in order to scan for BLE devices."
                isCancelable = false
                positiveButton(android.R.string.ok) {
                    requestPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
                negativeButton(android.R.string.cancel) {
                    Toast.makeText(
                        this@BluetoothActivity,
                        "Location permission is required. Please go to settings to give permission as it is important for the working of app.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }.show()
        }
    }

    private fun Activity.requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_DENIED) {
                    requestLocationPermission()
                } else {
                    startBleScan()
                }
            }
        }
    }


    fun readChar(cusServiceUuid: UUID,cusCharUuid : UUID): Int {
        //l.clear()
        var gatt = gble
        //val batteryServiceUuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
        //= UUID.fromString("00002101-0000-1000-8000-00805f9b34fb")
        val cusChar = gatt
            .getService(cusServiceUuid)?.getCharacteristic(cusCharUuid)

        flag = true
        gatt.readCharacteristic(cusChar)
        while(flag)
        {

        }
        return cusChar!!.value.first().toInt()
    }
    fun readPPG(): ArrayList<Int> {
        var gatt = gble
        var ppg_signal = ArrayList<Int>()
        val PPGserviceUuid = UUID.fromString("00002101-0000-1000-8000-00805f9b34fb")
        val PPGcharuuid= UUID.fromString("00003107-0000-1000-8000-00805f9b34fb")
        val PPGChar = gatt
            .getService(PPGserviceUuid)?.getCharacteristic(PPGcharuuid)

        for (i in 1..1024){
            gatt.readCharacteristic(PPGChar)
            while(flag)
            {

            }
            ppg_signal.add(PPGChar!!.value.first().toInt())
        }
        return ppg_signal
    }


}