package com.example.feetwings.activity

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*

open class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    /**
     * function to get day of the week
     */
    fun getDaysAgoDayOfTheWeek(daysAgo: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        Log.e("Fragment", calendar.time.toString())
        return calendar.time.toString().substring(0, 3)
    }


    /**
     * function to get date
     */
    fun getDaysAgoDate(daysAgo: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        Log.e("Fragment", calendar.time.toString())
        return calendar.time.toString().substring(8, 10)
    }

    /**
     * function to get current user ID.
     */
    fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }



}