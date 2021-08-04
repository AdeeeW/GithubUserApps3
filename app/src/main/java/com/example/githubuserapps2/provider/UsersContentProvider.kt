package com.example.githubuserapps2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuserapps2.database.DatabaseFavorite
import com.example.githubuserapps2.database.dao.FavoriteDao

class UsersContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.githubuserapps2"
        const val TABLE_NAME = "user_favorite"
        const val ID_FAVORITE = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_FAVORITE)
        }
    }

    private lateinit var userFavoriteDao: FavoriteDao

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        userFavoriteDao = context?.let {
            DatabaseFavorite.getDatabase(it)?.favoriteDao()
        }!!
        return false
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        var cursor: Cursor?
        when (uriMatcher.match(uri)) {
            ID_FAVORITE -> {
                cursor = userFavoriteDao.findAll()
                if (context != null) {
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        return 0
    }
}