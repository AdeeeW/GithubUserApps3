package com.example.githubuserapps2.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuserapps2.database.DatabaseFavorite
import com.example.githubuserapps2.database.dao.FavoriteDao
import com.example.githubuserapps2.database.entity.UserFavorite

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteDao?
    private var databaseFav: DatabaseFavorite?

    init {
        databaseFav = DatabaseFavorite.getDatabase(application)
        userDao = databaseFav?.favoriteDao()
    }

    fun getFavorite(): LiveData<List<UserFavorite>>?{
        return userDao?.getFavorite()
    }
}