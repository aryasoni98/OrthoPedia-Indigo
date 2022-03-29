package com.example.feetwings.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.feetwings.R
import kotlinx.android.synthetic.main.activity_introduction.*

class IntroductionActivity : AppCompatActivity() {

    private var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        btn_next.setOnClickListener {
            updateIntroductionScreen(count)
            count++
        }

        btn_getStarted.setOnClickListener {
            hideIntroScreen()
        }

        btn_login.setOnClickListener {
            val intent = Intent(this@IntroductionActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
    private fun hideIntroScreen(){
        cl_logoScreen.visibility = View.GONE
        ll_algorithm.visibility = View.VISIBLE
        btn_next.visibility = View.VISIBLE

    }

    private fun updateIntroductionScreen(count : Int){
        when (count) {
            1 -> {
                ll_algorithm.visibility = View.GONE
                ll_foot_temperature_and_pressure_monitoring.visibility = View.VISIBLE
                btn_next.visibility = View.GONE
                btn_login.visibility = View.VISIBLE
            }

        }
    }


}

