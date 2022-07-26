package com.example.android_firebase.activity

import android.os.Bundle
import com.example.android_firebase.databinding.ActivitySignInBinding
import com.example.android_firebase.manager.AuthHandler
import com.example.android_firebase.manager.AuthManager
import com.example.android_firebase.utils.Extensions.toast

class SignInActivity : BaseActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            bSignIn.setOnClickListener {
                firebaseSignIn(etEmail.text.toString(), etPassword.text.toString())
            }
            tvSignUp.setOnClickListener { callSignUpActivity(context) }
        }
    }

    private fun firebaseSignIn(email: String, password: String) {
        showLoading(this)
        AuthManager.signIn(email, password, object : AuthHandler {
            override fun onSuccess() {
                dismissLoading()
                toast("Signed in successfully!")
                callMainActivity(context)
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                if (AuthManager.checkEmail(email)) {
                    toast("ERROR INVALID EMAIL")
                } else {
                    toast("ERROR WRONG PASSWORD")
                }
            }
        })
    }
}