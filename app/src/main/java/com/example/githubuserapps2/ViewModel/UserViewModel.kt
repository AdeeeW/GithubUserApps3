package com.example.githubuserapps2.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapps2.Respons.ModelUser
import com.example.githubuserapps2.Respons.UserRespon
import com.example.githubuserapps2.api.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    val listUsers = MutableLiveData<ArrayList<ModelUser>>()

    fun setSearchUser(query: String){
        Retrofit.instance.getSearchUser(query).enqueue(object : Callback<UserRespon> {
            override fun onResponse(call: Call<UserRespon>, response: Response<UserRespon>) {
                if (response.isSuccessful) {
                    listUsers.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<UserRespon>, t: Throwable) {
                t.message?.let { Log.d("onFailure", it) }
            }
        })
    }

    fun getSearchUser(): LiveData<ArrayList<ModelUser>>{
        return listUsers
    }
}