package com.example.feetwings.activity


import android.os.Bundle
import android.os.Handler
import com.example.feetwings.R
import kotlinx.android.synthetic.main.activity_foot_live_temperature.*
import java.util.*


class FootLiveTemperatureActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foot_live_temperature)
        setupActionBar()
        setUpDateWidget()
        getTemperature()

    }

    /**
     * function to setup the action bar of activity.
     */
    private fun setupActionBar() {
        setSupportActionBar(toolbar_foot_live_temperature_activity)
        toolbar_foot_live_temperature_activity?.setNavigationIcon(R.drawable.ic_baseline_arrow_back)
        toolbar_foot_live_temperature_activity?.title = "Foot Temperature"
        toolbar_foot_live_temperature_activity?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    /**
     * function to set up date widget.
     */
    private fun setUpDateWidget() {
        tv_act_footLiveTemperature_day_of_the_week_1.text = getDaysAgoDayOfTheWeek(4)
        tv_act_footLiveTemperature_day_of_the_week_2.text = getDaysAgoDayOfTheWeek(3)
        tv_act_footLiveTemperature_day_of_the_week_3.text = getDaysAgoDayOfTheWeek(2)
        tv_act_footLiveTemperature_day_of_the_week_4.text = getDaysAgoDayOfTheWeek(1)
        tv_act_footLiveTemperature_day_of_the_week_6.text = getDaysAgoDayOfTheWeek(-1)
        tv_act_footLiveTemperature_day_of_the_week_7.text = getDaysAgoDayOfTheWeek(-2)

        tv_act_footLiveTemperature_date_1.text = getDaysAgoDate(4)
        tv_act_footLiveTemperature_date_2.text = getDaysAgoDate(3)
        tv_act_footLiveTemperature_date_3.text = getDaysAgoDate(2)
        tv_act_footLiveTemperature_date_4.text = getDaysAgoDate(1)
        tv_act_footLiveTemperature_date_5.text = getDaysAgoDate(0)
        tv_act_footLiveTemperature_date_6.text = getDaysAgoDate(-1)
        tv_act_footLiveTemperature_date_7.text = getDaysAgoDate(-2)
    }
    private fun getTemperature() {
        t1l.text = BluetoothActivity().readChar(UUID.fromString("00002101-0000-1000-8000-00805f9b34fb"),UUID.fromString("00003101-0000-1000-8000-00805f9b34fb")).toString()
        t2l.text = BluetoothActivity().readChar(UUID.fromString("00002101-0000-1000-8000-00805f9b34fb"),UUID.fromString("00003102-0000-1000-8000-00805f9b34fb")).toString()
        t3l.text = BluetoothActivity().readChar(UUID.fromString("00002101-0000-1000-8000-00805f9b34fb"),UUID.fromString("00003103-0000-1000-8000-00805f9b34fb")).toString()
        t4l.text = BluetoothActivity().readChar(UUID.fromString("00002101-0000-1000-8000-00805f9b34fb"),UUID.fromString("00003104-0000-1000-8000-00805f9b34fb")).toString()
        t1r.text = BluetoothActivity().readChar(UUID.fromString("00002101-0000-1000-8000-00805f9b34fb"),UUID.fromString("00003101-0000-1000-8000-00805f9b34fb")).toString()
        t2r.text = BluetoothActivity().readChar(UUID.fromString("00002101-0000-1000-8000-00805f9b34fb"),UUID.fromString("00003102-0000-1000-8000-00805f9b34fb")).toString()
        t3r.text = BluetoothActivity().readChar(UUID.fromString("00002101-0000-1000-8000-00805f9b34fb"),UUID.fromString("00003103-0000-1000-8000-00805f9b34fb")).toString()
        t4r.text = BluetoothActivity().readChar(UUID.fromString("00002101-0000-1000-8000-00805f9b34fb"),UUID.fromString("00003104-0000-1000-8000-00805f9b34fb")).toString()
    }

}