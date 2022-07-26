package com.example.android_firebase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_firebase.databinding.ActivityUpdateBinding
import com.example.android_firebase.model.Post

class UpdateActivity : AppCompatActivity() {

    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            ivClose.setOnClickListener { finish() }

            val post = intent.getSerializableExtra("post")
            post as Post
            etTitle.setText(post.title)
            etBody.setText(post.body)

            bUpdate.setOnClickListener {
                val post2 = Post(post.id.toString(),etTitle.text.toString(),etBody.text.toString())
                backToFinish(post2)
            }
        }
    }

    private fun backToFinish(post: Post) {
        val intent = Intent()
        intent.putExtra("returnPost",post)
        setResult(RESULT_OK,intent)
        finish()
    }
}