package com.example.android_firebase.activity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.android_firebase.database.UserRepository
import com.example.android_firebase.databinding.ActivityMainBinding
import com.example.android_firebase.manager.AuthManager
import com.example.android_firebase.model.User

class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            bSignOut.setOnClickListener {
                AuthManager.signOut()
                callSignInActivity(context)
            }
        }
    }
}