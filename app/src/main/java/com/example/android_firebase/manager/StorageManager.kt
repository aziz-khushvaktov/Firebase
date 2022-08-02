package com.example.android_firebase.manager

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

object StorageManager {

    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
    val photosReference = storageReference.child("photos")

    fun uploadPhoto(uri: Uri, handler: StorageHandler) {
        val fileName = System.currentTimeMillis().toString()
        val uploadTask: UploadTask = photosReference.child(fileName).putFile(uri)
        uploadTask.addOnSuccessListener {
            val result = it.metadata!!.reference!!.downloadUrl
            result.addOnSuccessListener {
                val imgUri = it.toString()
                handler.onSuccess(imgUri)
            }.addOnFailureListener { e->
                handler.onError(e)
            }
        }.addOnFailureListener{ e->
            handler.onError(e)
        }
    }
}