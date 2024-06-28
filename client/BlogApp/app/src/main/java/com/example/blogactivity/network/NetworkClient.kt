package com.example.blogactivity.network

import com.example.blogactivity.util.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {
    companion object{
        private var networkClient: Retrofit? = null

        fun getNetworkClient(): Retrofit {
            val tempInstance = networkClient
            return if(tempInstance!=null){
                tempInstance
            }else{
                val instance = Retrofit.Builder()
                    .baseUrl(Configuration.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                networkClient = instance
                instance
            }
        }
    }
}