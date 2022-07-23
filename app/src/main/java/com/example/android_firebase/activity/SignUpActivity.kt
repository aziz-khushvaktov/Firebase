package com.example.android_firebase.activity

import android.os.Bundle
import com.example.android_firebase.database.UserRepository
import com.example.android_firebase.databinding.ActivitySignUpBinding
import com.example.android_firebase.manager.AuthHandler
import com.example.android_firebase.manager.AuthManager
import com.example.android_firebase.model.User
import com.example.android_firebase.utils.Extensions.toast
import java.lang.Exception

class SignUpActivity : BaseActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            bSignUp.setOnClickListener { firebaseSignUp(etEmail.text.toString(),etPassword.text.toString()) }

            tvSignIn.setOnClickListener { finish() }
        }
    }

    private fun firebaseSignUp(email: String, password: String) {
        showLoading(this)
        AuthManager.signUp(email,password,object : AuthHandler {
            override fun onSuccess() {
                dismissLoading()
                val user = User(null,binding.etEmail.toString().trim(),binding.etFullName.text.toString(),binding.etPassword.text.toString().trim(),
                binding.etCPassword.text.toString().trim())
                saveUserToDB(user)
                toast("Signed up successfully!")
                callMainActivity(context)
            }
            override fun onError(exception: Exception?) {
                dismissLoading()
                if(!AuthManager.checkEmail(email)) {
                    toast("ERROR CREDENTIAL ALREADY IN USE!")
                }else {
                    toast("Sign up failed!")
                }
            }
        })
    }

    private fun saveUserToDB(user: User) {
        val userRepository = UserRepository(application)
        userRepository.saveUser(user)
    }

}