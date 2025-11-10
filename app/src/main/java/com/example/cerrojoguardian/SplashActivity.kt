package com.example.cerrojoguardian

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("CerrojoGuardianPrefs", Context.MODE_PRIVATE)
        val isSetupComplete = sharedPreferences.getBoolean("is_setup_complete", false)

        val intent = if (isSetupComplete) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, SetupActivity::class.java)
        }

        startActivity(intent)
        finish()
    }
}

