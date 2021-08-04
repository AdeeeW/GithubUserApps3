package com.example.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUser(
        val id: Int = 0,
        var login: String? = null,
        var avatar_url: String? = null,
        var type: String? = null,
) : Parcelable
