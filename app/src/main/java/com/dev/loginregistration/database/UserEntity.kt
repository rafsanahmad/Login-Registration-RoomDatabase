package com.dev.loginregistration.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,

    @ColumnInfo(name = "full_name")
    var fullName: String,

    @ColumnInfo(name = "user_name")
    var userName: String,

    @ColumnInfo(name = "password_text")
    var passwrd: String
)