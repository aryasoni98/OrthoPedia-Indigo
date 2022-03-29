package com.example.feetwings.activity

import android.os.Bundle
import com.example.feetwings.R
import kotlinx.android.synthetic.main.activity_subscription.*

class SubscriptionActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)
        setupActionBar()
    }

    /**
     * function to setup the action bar of activity.
     */
    private fun setupActionBar() {
        setSupportActionBar(toolbar_subscription_activity)
        toolbar_subscription_activity?.setNavigationIcon(R.drawable.ic_baseline_arrow_back)
        toolbar_subscription_activity?.title = "Subscription Plans"
        toolbar_subscription_activity?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}