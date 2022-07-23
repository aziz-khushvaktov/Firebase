package com.example.android_firebase.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.example.android_firebase.databinding.ActivitySplashBinding
import com.example.android_firebase.manager.AuthManager

class SplashActivity : BaseActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        countDownTimer()
    }


    private fun countDownTimer() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                if (AuthManager.isSignedIn()) {
                    callMainActivity(context)
                    finish()
                } else {
                    callSignInActivity(context)
                    finish()
                }
            }
        }.start()
    }

}