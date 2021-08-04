package com.example.consumerapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.consumerapp.model.ModelUser
import com.example.consumerapp.`object`.DatabaseContract
import com.example.consumerapp.`object`.MappingHelper

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private val list = MutableLiveData<ArrayList<ModelUser>>()

    fun setFavoriteUser(context: Context){
        val cursor = context.contentResolver.query(DatabaseContract.FavoriteUserColums.CONTENT_URI, null, null, null, null)
        val listConvert = MappingHelper.mapCursor(cursor)
        list.postValue(listConvert)
    }

    fun getFavorite(): LiveData<ArrayList<ModelUser>>{
        return list
    }
}