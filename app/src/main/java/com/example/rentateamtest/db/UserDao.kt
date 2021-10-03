package com.example.rentateamtest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.rentateamtest.pojo.User
import io.reactivex.Flowable

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    fun loadAll(): Flowable<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun load(id: Long): Flowable<User>

    @Insert(onConflict=REPLACE)
    fun save(user: User)
}
