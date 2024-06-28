package com.example.blogactivity.network

import com.example.blogactivity.contract.Request
import com.example.blogactivity.contract.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IRequestContract {
    @POST("service.php")
    fun makeApiCall(@Body request: Request): Call<Response>
}