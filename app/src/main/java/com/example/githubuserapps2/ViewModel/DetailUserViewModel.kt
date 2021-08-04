package com.example.githubuserapps2.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapps2.Respons.ModelUser
import com.example.githubuserapps2.api.Retrofit
import com.example.githubuserapps2.database.DatabaseFavorite
import com.example.githubuserapps2.database.dao.FavoriteDao
import com.example.githubuserapps2.database.entity.UserFavorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val users = MutableLiveData<ModelUser>()

    private var userDao: FavoriteDao?
    private var databaseFav: DatabaseFavorite?

    init {
        databaseFav = DatabaseFavorite.getDatabase(application)
        userDao = databaseFav?.favoriteDao()
    }

    fun setUserDetail(username: String) {
        Retrofit.instance
                .getDetailUser(username)
                .enqueue(object : Callback<ModelUser> {
                    override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                        if (response.isSuccessful) {
                            users.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<ModelUser>, t: Throwable) {
                        Log.d("onFailure", t.message.toString())
                    }

                })
    }

    fun getUserDetails(): LiveData<ModelUser> {
        return users
    }

    fun addFavToDatabase(id: Int, username: String, avatar: String?, type: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserFavorite(
                    id,
                    username,
                    avatar,
                    type
            )
            userDao?.addFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkFavorite(id)

    fun deleteFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavorite(id)
        }
    }
}