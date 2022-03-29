package com.example.feetwings.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.feetwings.R
import com.example.feetwings.activity.BaseActivity
import com.example.feetwings.activity.FootLiveTemperatureActivity
import kotlinx.android.synthetic.main.fragment_home.*


class  HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUpDateWidget()


        ll_home_fragment_live_temperature.setOnClickListener {
            startActivity(Intent(activity, FootLiveTemperatureActivity::class.java))
        }
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * function to set up date widget.
     */
    private fun setUpDateWidget() {
        tv_frag_home_day_of_the_week_1.text = BaseActivity().getDaysAgoDayOfTheWeek(4)
        tv_frag_home_day_of_the_week_2.text = BaseActivity().getDaysAgoDayOfTheWeek(3)
        tv_frag_home_day_of_the_week_3.text = BaseActivity().getDaysAgoDayOfTheWeek(2)
        tv_frag_home_day_of_the_week_4.text = BaseActivity().getDaysAgoDayOfTheWeek(1)
        tv_frag_home_day_of_the_week_6.text = BaseActivity().getDaysAgoDayOfTheWeek(-1)
        tv_frag_home_day_of_the_week_7.text = BaseActivity().getDaysAgoDayOfTheWeek(-2)

        tv_frag_home_date_1.text = BaseActivity().getDaysAgoDate(4)
        tv_frag_home_date_2.text = BaseActivity().getDaysAgoDate(3)
        tv_frag_home_date_3.text = BaseActivity().getDaysAgoDate(2)
        tv_frag_home_date_4.text = BaseActivity().getDaysAgoDate(1)
        tv_frag_home_date_5.text = BaseActivity().getDaysAgoDate(0)
        tv_frag_home_date_6.text = BaseActivity().getDaysAgoDate(-1)
        tv_frag_home_date_7.text = BaseActivity().getDaysAgoDate(-2)
    }



}