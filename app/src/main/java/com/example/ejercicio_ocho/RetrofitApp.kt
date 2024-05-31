package com.example.ejercicio_ocho

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApp {
    companion object {
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(IContact.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}