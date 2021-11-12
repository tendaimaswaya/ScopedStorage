package com.demo.scopedstorage.interfaces

import retrofit2.Retrofit
import retrofit2.http.Url
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface IRetrofit {
    @GET
    fun download(@Url fileUrl: String?, @Header("Authorization") token:String): Call<ResponseBody?>?

    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://base_url_here/") // always END with /
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}