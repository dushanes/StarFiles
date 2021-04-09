package com.starfiles.api

import com.google.gson.GsonBuilder
import com.starfiles.data.models.Results
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiUtilities: SwapiAPI {
    private val swapiAPI: SwapiAPI by  lazy {
        val gson = GsonBuilder().serializeNulls().create()

        val client = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://swapi.dev")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return@lazy retrofit.create(SwapiAPI::class.java)
    }

    override fun getCharactersByPage(position: Int, query: String): Call<Results> {
        return swapiAPI.getCharactersByPage(position, query)
    }
}