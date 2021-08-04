package com.example.githubuserapps2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserapps2.database.dao.FavoriteDao
import com.example.githubuserapps2.database.entity.UserFavorite

@Database(
    entities = [UserFavorite::class],
    version = 1
)
abstract class DatabaseFavorite: RoomDatabase() {
    companion object{
        var INSTACE : DatabaseFavorite? = null

        fun getDatabase(context: Context): DatabaseFavorite?{
            if (INSTACE == null){
                synchronized(DatabaseFavorite::class){
                    INSTACE = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseFavorite::class.java,
                        "fav_database"
                    ).build()
                }
            }
            return INSTACE
        }
    }

    abstract fun favoriteDao(): FavoriteDao
}