package com.example.githubuserapps2.Respons

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUser(
        val id: Int = 0,
        var login: String? = null,
        var avatar_url: String? = null,
        var type: String? = null,
        var name: String? = null,
        var location: String? = null,
        var followers: Int = 0,
        var following: Int = 0,
        var public_repos: Int = 0
) : Parcelable
