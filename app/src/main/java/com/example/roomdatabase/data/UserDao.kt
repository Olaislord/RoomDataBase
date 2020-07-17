package com.example.roomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomdatabase.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)// am using user -> User.kt

    @Delete
    suspend fun deleteUser(user: User) // am using user to represent User class (User.kt)

    @Update
    suspend fun updateUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUser()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllUser(): LiveData<List<User>>
}