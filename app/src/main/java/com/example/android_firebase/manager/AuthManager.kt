package com.example.android_firebase.manager

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser

object AuthManager {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser: FirebaseUser? = firebaseAuth.currentUser

    fun isSignedIn(): Boolean {
        return firebaseUser != null
    }

    fun checkEmail(email: String): Boolean {
        //return firebaseAuth.isSignInWithEmailLink(email)
        //Log.d("checkEmail", firebaseUser.toString())
        return firebaseAuth.isSignInWithEmailLink(email)
    }


    fun signIn(email: String, password: String, handler: AuthHandler) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                handler.onSuccess()
            }else {
                handler.onError(task.exception)
            }
        }
    }

    fun signUp(email: String, password: String, handler: AuthHandler) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                handler.onSuccess()
            }else {
                handler.onError(task.exception)
            }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}