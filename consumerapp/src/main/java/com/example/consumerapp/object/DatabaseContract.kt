package com.example.consumerapp.`object`

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.example.githubuserapps2"
    const val SCHEME = "content"

    internal class FavoriteUserColums: BaseColumns{
        companion object{
            const val TABLE_NAME = "user_favorite"
            const val ID = "id"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar"
            const val TYPE = "type"

            val CONTENT_URI = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}