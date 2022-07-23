package com.example.android_firebase.database

import android.app.Application
import com.example.android_firebase.manager.RoomManager
import com.example.android_firebase.model.User

class UserRepository(application: Application) {

    var userDao: UserDao

    init {
        val db = RoomManager.getDatabase(application)
        userDao = db.userDao()
    }

    fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    fun saveUser(user: User) {
        userDao.saveUser(user)
    }
}