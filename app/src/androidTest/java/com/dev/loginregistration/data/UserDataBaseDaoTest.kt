/*
 * *
 *  * Created by rafsanahmad on 7/11/21, 6:02 AM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/11/21, 6:02 AM
 *
 */

package com.dev.loginregistration.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dev.loginregistration.database.UserDatabase
import com.dev.loginregistration.database.UserDatabaseDao
import com.dev.loginregistration.database.UserEntity
import com.dev.loginregistration.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDataBaseDaoTest {
    private lateinit var database: UserDatabase
    private lateinit var userDataBaseDao: UserDatabaseDao
    private val userA = UserEntity(1, "User one", "Test user", "Aaaaa1")
    private val userB = UserEntity(2, "User Two", "Test user2", "Aaaaa1")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build()
        userDataBaseDao = database.userDatabaseDao

        // Insert plants in non-alphabetical order to test that results are sorted by name
        userDataBaseDao.insert(userA)
        userDataBaseDao.insert(userB)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetUsers() {
        val userList = getValue(userDataBaseDao.getAllUsers())
        Assert.assertThat(userList.size, Matchers.equalTo(2))
    }

    @Test
    fun testGetUser() = runBlocking {
        Assert.assertThat(
            userDataBaseDao.getUsername("Test user"),
            Matchers.equalTo(userA)
        )
        Assert.assertThat(
            userDataBaseDao.getUsername("Test user2"),
            Matchers.equalTo(userB)
        )
    }

    @Test
    fun testDeleteUsers() = runBlocking {
        userDataBaseDao.deleteAll()
        val userList = getValue(userDataBaseDao.getAllUsers())
        Assert.assertThat(userList.size, Matchers.equalTo(0))
    }
}