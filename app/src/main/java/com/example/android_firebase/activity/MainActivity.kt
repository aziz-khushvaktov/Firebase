package com.example.android_firebase.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_firebase.R
import com.example.android_firebase.adapter.PosterAdapter
import com.example.android_firebase.database.DatabaseHandler
import com.example.android_firebase.database.DatabaseManager
import com.example.android_firebase.databinding.ActivityMainBinding
import com.example.android_firebase.helper.SwipeHelper
import com.example.android_firebase.manager.AuthManager
import com.example.android_firebase.model.Post
import com.example.android_firebase.utils.Extensions.toast

class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { PosterAdapter() }
    var items: ArrayList<Post> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initViews()
    }

    private fun initViews() {
        apiLoadAllPosts()
        swipeRecycler()
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 1)
            recyclerView.adapter = adapter

            ivLogout.setOnClickListener {
                AuthManager.signOut()
                callSignInActivity(context)
            }
            fabCreate.setOnClickListener { callCreateActivity() }
        }
    }

    private fun apiLoadAllPosts() {
        showLoading(this@MainActivity)
        DatabaseManager.loadPost(object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                dismissLoading()
                items.clear()
                items.addAll(posts)
                adapter.submitData(items)
            }

            override fun onError() {
                dismissLoading()
            }
        })
    }

    private fun callCreateActivity() {
        val intent = Intent(this, CreateActivity::class.java)
        resultLauncher.launch(intent)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                // Load all posts...
                apiLoadAllPosts()
            }
        }

    private fun swipeRecycler() {
        object : SwipeHelper(this, binding.recyclerView, false) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?,
            ) {
                // Delete
                underlayButtons?.add(UnderlayButton("Delete",
                    AppCompatResources.getDrawable(this@MainActivity,
                        R.drawable.ic_baseline_delete_24),
                    resources.getColor(R.color.red),
                    Color.parseColor("#FF3700B3")) { pos: Int ->
                    Toast.makeText(this@MainActivity, "Deleted click at $pos", Toast.LENGTH_SHORT)
                        .show()
                    deletePost(items[pos])
                    items.clear()
                })
                // Update
                underlayButtons?.add(UnderlayButton("Update",
                    AppCompatResources.getDrawable(this@MainActivity,
                        R.drawable.ic_baseline_cloud_upload_24),
                    resources.getColor(R.color.green),
                    Color.parseColor("#FF3700B3")

                ) { pos: Int ->
                    callUpdateActivity(items[pos])
                })
            }
        }
    }

    private fun callUpdateActivity(post: Post) {
        val intent = Intent(this,UpdateActivity::class.java)
        intent.putExtra("post",post)
        updateLauncher.launch(intent)
    }
    var updateLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data: Intent? = result.data
        val post  = data!!.getSerializableExtra("returnPost")
        Log.d("updatePost", post.toString())
        updatePost(post as Post)
    }

    private fun deletePost(post: Post) {
        showLoading(this)
        DatabaseManager.deletePost(post,object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                apiLoadAllPosts()
                dismissLoading()
                toast("Post deleted successfully!")
            }

            override fun onError() {
                dismissLoading()
                toast("Deleting post failed!")
            }
        })
    }

    private fun updatePost(post: Post) {
        DatabaseManager.updatePost(post,object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                toast("Post updated successfully!")
            }

            override fun onError() {
                toast("Updating post failed!")
            }
        })
    }
}