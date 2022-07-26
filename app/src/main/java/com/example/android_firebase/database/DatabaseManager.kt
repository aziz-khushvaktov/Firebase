package com.example.android_firebase.database

import com.example.android_firebase.model.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object DatabaseManager {
    private var database = FirebaseDatabase.getInstance().reference
    private var reference = database.child("posts")

    fun storePost(post: Post, handler: DatabaseHandler) {
        val key = reference.push().key
        if (key == null) return
        post.id = key
        reference.child(key).setValue(post).addOnSuccessListener {
            handler.onSuccess()
        }.addOnFailureListener {
            handler.onError()
        }
    }

    fun loadPost(handler: DatabaseHandler) {
        reference.addValueEventListener(object : ValueEventListener {
            val posts: ArrayList<Post> = ArrayList()

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val post: Post? = snapshot.getValue(Post::class.java)
                        post.let { posts.add(post!!) }
                    }
                    handler.onSuccess(posts = posts)
                } else {
                    handler.onSuccess(posts = ArrayList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                handler.onError()
            }
        })
    }

    fun deletePost(post: Post, handler: DatabaseHandler) {
        database.child("posts").child(post.id.toString()).removeValue()
    }

    fun updatePost(post: Post, handler: DatabaseHandler) {
        database.child("posts").child(post.id.toString()).setValue(post)
    }
}