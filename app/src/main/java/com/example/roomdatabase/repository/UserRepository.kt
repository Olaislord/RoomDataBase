package com.example.roomdatabase.repository

import androidx.lifecycle.LiveData
import com.example.roomdatabase.model.User
import com.example.roomdatabase.data.UserDao

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllUser()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}