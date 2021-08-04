package com.example.consumerapp.`object`

import android.database.Cursor
import com.example.consumerapp.model.ModelUser

object MappingHelper {
    fun mapCursor(cursor: Cursor?): ArrayList<ModelUser>{
        val list = ArrayList<ModelUser>()

        if (cursor != null){
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColums.ID))
                val login = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColums.USERNAME))
                val avatar_url = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColums.AVATAR_URL))
                val type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColums.TYPE))

                list.add(
                    ModelUser(
                        id,
                        avatar_url,
                        login,
                        type
                    )
                )
            }
        }
        return list
    }
}