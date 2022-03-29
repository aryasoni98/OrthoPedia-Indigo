package com.example.feetwings.activity

import android.os.Bundle
import com.example.feetwings.R
import kotlinx.android.synthetic.main.activity_blood_pressure.*

class BloodPressureActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_pressure)

        setupActionBar()
        predBp()
    }

    /**
     * function to setup the action bar of activity.
     */
    private fun setupActionBar() {
        setSupportActionBar(toolbar_blood_pressure)
        toolbar_blood_pressure?.setNavigationIcon(R.drawable.ic_baseline_arrow_back)
        toolbar_blood_pressure?.title = "Foot Temperature"
        toolbar_blood_pressure?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    private fun predBp(){
        val ppg_signal = BluetoothActivity().readPPG()
        systolic.text = ppg_signal.minOrNull().toString()
        diastolic.text = ppg_signal.maxOrNull().toString()
    }
}