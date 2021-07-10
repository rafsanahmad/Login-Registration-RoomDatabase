/*
 * *
 *  * Created by rafsanahmad on 7/10/21, 11:55 PM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/10/21, 11:55 PM
 *
 */

package com.dev.loginregistration.database

class UserRepository(private val dao: UserDatabaseDao) {

    val users = dao.getAllUsers()
    suspend fun insert(user: UserEntity) {
        return dao.insert(user)
    }

    suspend fun getUserName(userName: String): UserEntity? {
        return dao.getUsername(userName)
    }

    //suspend fun deleteAll(): Int {
    //    return dao.deleteAll()
    //}

}