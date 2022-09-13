package com.example.demophonenoverification

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 100
    var telephonyManager: TelephonyManager? = null
    lateinit var phone_number: TextView
    lateinit var btn: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvDisplay: TextView = findViewById(R.id.tv_displayInfo)
        phone_number = findViewById(R.id.phone_number)
        btn = findViewById(R.id.btn)

        tvDisplay.text = getSystemDetail()

        btn.setOnClickListener {

            val intent = Intent(this , PhoneInformationActivity::class.java)
            startActivity(intent)


        }


        }


    @SuppressLint("HardwareIds")
    private fun getSystemDetail(): String {
        return "Brand: ${Build.BRAND} \n" +
                "DeviceID: ${
                    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                } \n" +
                "Model: ${Build.MODEL} \n" +
                "ID: ${Build.ID} \n" +
                "SDK: ${Build.VERSION.SDK_INT} \n" +
                "Manufacture: ${Build.MANUFACTURER} \n" +
                "Brand: ${Build.BRAND} \n" +
                "User: ${Build.USER} \n" +
                "Type: ${Build.TYPE} \n" +
                "Base: ${Build.VERSION_CODES.BASE} \n" +
                "Incremental: ${Build.VERSION.INCREMENTAL} \n" +
                "Board: ${Build.BOARD} \n" +
                "Host: ${Build.HOST} \n" +
                "FingerPrint: ${Build.FINGERPRINT} \n" +
                "Version Code: ${Build.VERSION.RELEASE}" +


                Log.d(TAG, "onCreate: Version + ${Build.VERSION.RELEASE}")

    }

}