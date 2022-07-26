package com.example.android_firebase.activity

import android.content.Intent
import android.os.Bundle
import com.example.android_firebase.database.DatabaseHandler
import com.example.android_firebase.database.DatabaseManager
import com.example.android_firebase.databinding.ActivityCreateBinding
import com.example.android_firebase.model.Post
import com.example.android_firebase.utils.Extensions.toast

class CreateActivity : BaseActivity() {

    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            ivClose.setOnClickListener { finish() }

            bCreate.setOnClickListener {
                val title = etTitle.text.toString().trim()
                val body = etBody.text.toString().trim()
                if (title.isNotEmpty() && body.isNotEmpty()) {
                    val post = Post(title, body)
                    apiStoreDatabase(post)
                } else {
                    toast("Enter post parameters!")
                }
            }
        }
    }

    private fun apiStoreDatabase(post: Post) {
        showLoading(this)
        DatabaseManager.storePost(post, object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                dismissLoading()
                toast("Post saved successfully!")
                finishIntent()
            }

            override fun onError() {
                toast("Saving post failed!")
                dismissLoading()
            }
        })
    }

    private fun finishIntent() {
        val returnIntent = Intent()
        setResult(RESULT_OK, returnIntent)
        finish()
    }
}