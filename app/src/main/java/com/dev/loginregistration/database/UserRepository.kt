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