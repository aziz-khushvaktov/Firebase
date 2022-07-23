package com.example.android_firebase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_table")
open class User(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "fullName") val fullName: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "cPassword") val cPassword: String,
) :
    Serializable {
    override fun toString(): String {
        return "User { " + "id = " + id + ", email = " + email +  ", fullName = " + fullName +
                ", password = " + password + ", confirmPassword = " + cPassword + " }"
    }
}