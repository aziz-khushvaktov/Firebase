package com.example.android_firebase.manager

import java.lang.Exception

interface AuthHandler {
    fun onSuccess()
    fun onError(exception: Exception?)
}