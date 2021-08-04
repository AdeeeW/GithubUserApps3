package com.example.githubuserapps2.api

import com.example.githubuserapps2.Respons.ModelUser
import com.example.githubuserapps2.Respons.UserRespon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String?
    ): Call<ModelUser>

    @GET("search/users")
    fun getSearchUser(
        @Query("q") q: String?
    ): Call<UserRespon>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String?
    ): Call<ArrayList<ModelUser>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String?
    ): Call<ArrayList<ModelUser>>
}