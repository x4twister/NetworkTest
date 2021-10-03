package com.example.rentateamtest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rentateamtest.pojo.User

@Database(entities = [User::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
}