package com.example.githubuserapps2.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull


@Entity(tableName = "user_favorite")
@Parcelize
data class UserFavorite(
        @PrimaryKey @NotNull val id: Int,
        @ColumnInfo(name = "avatar") val avatar_url: String?,
        @ColumnInfo(name = "username") val login: String?,
        @ColumnInfo(name = "type") val type: String?,
) : Parcelable
