package com.example.android_firebase.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toIcon
import androidx.core.net.toUri
import com.example.android_firebase.database.DatabaseHandler
import com.example.android_firebase.database.DatabaseManager
import com.example.android_firebase.databinding.ActivityCreateBinding
import com.example.android_firebase.manager.StorageHandler
import com.example.android_firebase.manager.StorageManager
import com.example.android_firebase.model.Post
import com.example.android_firebase.utils.Extensions.toast
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.FishBunCreator
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import java.lang.Exception

class CreateActivity : BaseActivity() {

    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }
    var pickedPhoto: Uri? = null
    var allPhotos: ArrayList<Uri> = ArrayList()
    val CAMERA_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {

            ivCamera.setOnClickListener { pickProfilePhoto() }

            ivClose.setOnClickListener { finish() }

            bCreate.setOnClickListener {
                val title = etTitle.text.toString().trim()
                val body = etBody.text.toString().trim()
                if (title.isNotEmpty() && body.isNotEmpty()) {
                    val post = Post(title, body)
                    apiStoreNewPost(post)
                } else {
                    toast("Enter post parameters!")
                }
            }
        }
    }

    private fun pickProfilePhoto() {
        allPhotos.clear()
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMinCount(1)
            .setMaxCount(1)
            .setCamera(isCamera = true)
            .setIsUseDetailView(false)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLauncher)

    }

    private fun pickImageFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,CAMERA_REQUEST)
    }

    private val photoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            allPhotos = it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
            pickedPhoto = allPhotos[0]
            binding.ivPhoto.setImageURI(pickedPhoto)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            val photo = data!!.extras!!.get("data")
            binding.ivPhoto.setImageBitmap(photo as Bitmap)
            pickedPhoto = photo.toString().toUri()
        }
    }



    private fun apiStoreNewPost(post: Post) {
        if(pickedPhoto != null) {
            apiStoreStorage(post)
        }else {
            apiStoreStorage(post)
        }
    }

    private fun apiStoreStorage(post: Post) {
        showLoading(this)
        StorageManager.uploadPhoto(pickedPhoto!!,object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                post.img = imgUrl
                apiStoreDatabase(post)
            }
            override fun onError(exception: Exception?) {
                apiStoreDatabase(post)
            }
        })
    }

    private fun apiStoreDatabase(post: Post) {
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