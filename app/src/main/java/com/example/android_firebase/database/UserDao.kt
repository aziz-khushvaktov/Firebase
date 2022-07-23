package com.example.android_firebase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_firebase.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)
}