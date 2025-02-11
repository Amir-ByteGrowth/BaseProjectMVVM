package com.amiritdev.kotlinbasewithcorutine.data.remote


import com.amiritdev.kotlinbasewithcorutine.data.models.PostsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<PostsResponse>
}