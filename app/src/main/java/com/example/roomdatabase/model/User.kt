package com.example.roomdatabase.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val firstname: String?,
    val lastname: String?,
    val age: Int
)