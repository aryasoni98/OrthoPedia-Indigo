package com.example.feetwings.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.feetwings.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpAnimations()

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser

        /**
         * If user is not authenticated, send him to IntroductionActivity.
         * else send him to BluetoothActivity after 1.3 seconds.
         */
        Handler().postDelayed({
            if (user != null) {
                startActivity(Intent(this, BluetoothActivity::class.java))
            } else {
                startActivity(Intent(this, IntroductionActivity::class.java))
            }
            finish()
        }, 1300)
    }


    /**
     * function to animate the SplashActivity.
     */
    private fun setUpAnimations() {
        val animationTopToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom)
        val animationBottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)
        iv_splash_activity_logo.startAnimation(animationTopToBottom)
        tv_splash_screen_app_name.startAnimation(animationBottomToTop)
        tv_splash_screen_app_name_italic.startAnimation(animationBottomToTop)
    }


}