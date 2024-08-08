package com.example.uasmobileprogramming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.content.Intent
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            goToMainActivity()
        }, 3000L)
    }

    private fun goToMainActivity() {
        Intent(this, SignInActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}