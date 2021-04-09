package com.starfiles.api

import com.starfiles.data.models.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SwapiAPI {
    //This call for all results and search, because absence of query is fine
    @GET("/api/people/")
    fun getCharactersByPage(
        @Query("page") page: Int,
        @Query("search") query: String
    ): Call<Results>
}