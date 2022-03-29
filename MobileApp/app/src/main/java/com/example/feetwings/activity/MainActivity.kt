package com.example.feetwings.activity

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.feetwings.R
import com.example.feetwings.fragments.HomeFragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*


class MainActivity : BaseActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()

        setUpNavigationBar()
        goToHomeFragment()
        updateNavigationBarDetails()

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.about_us -> {
                    Toast.makeText(this, "Clicked on About us", Toast.LENGTH_SHORT).show()
                }
                R.id.subscription_plan -> {
                    startActivity(Intent(this, SubscriptionActivity::class.java))
                }
                R.id.log_out -> {
                    AuthUI.getInstance().signOut(this).addOnCompleteListener {
                        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }


    private fun updateNavigationBarDetails() {
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val hView = nav_view.getHeaderView(0)
        val textViewName = hView.findViewById(R.id.tv_username) as TextView
        val profileImage = hView.findViewById(R.id.iv_user_image) as ImageView
        Glide
            .with(this@MainActivity)
            .load(user!!.photoUrl) // URL of the image
            .centerCrop() // Scale type of the image.
            .into(profileImage) // the view in which the image will be loaded.

        textViewName.text = user.displayName
    }

    /**
     * Function to set up the navigation bar
     */
    private fun setUpNavigationBar() {
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            )
        )
    }


    /**
     * function to change fragment to Home fragment
     */
    private fun goToHomeFragment() {
        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.fade_in, R.anim.fade_out)
            replace(R.id.fl_fragment, homeFragment)
            commit()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}



