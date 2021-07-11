/*
 * *
 *  * Created by rafsanahmad on 7/10/21, 11:55 PM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/10/21, 11:55 PM
 *
 */

package com.dev.loginregistration.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDatabaseDao {

    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("DELETE FROM users_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM users_table WHERE user_name LIKE :userName")
    suspend fun getUsername(userName: String): UserEntity?

}