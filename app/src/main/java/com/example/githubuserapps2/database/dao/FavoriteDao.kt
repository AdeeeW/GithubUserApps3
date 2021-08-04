package com.example.githubuserapps2.database.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.githubuserapps2.database.entity.UserFavorite

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite(userFavorite: UserFavorite)

    @Query("SELECT * FROM user_favorite")
    fun getFavorite(): LiveData<List<UserFavorite>>

    @Query("SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
    suspend fun checkFavorite(id: Int): Int

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    suspend fun deleteFavorite(id: Int): Int

    @Query("SELECT * FROM user_favorite")
    fun findAll(): Cursor
}