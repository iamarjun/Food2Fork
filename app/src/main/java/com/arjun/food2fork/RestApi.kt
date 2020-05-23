package com.arjun.food2fork

import com.arjun.food2fork.model.GetRecipe
import com.arjun.food2fork.model.SearchRecipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("get")
    suspend fun getRecipe(
        @Query("rId") rId: String
    ): GetRecipe

    @GET("search")
    suspend fun searchRecipe(
        @Query("q") query: String,
        @Query("page") page: Int
    ): SearchRecipe

}