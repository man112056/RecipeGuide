package com.sample.recipeguide

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = "b4e9c600cef645e28fb40b3e106a2115"
    ): RecipeResponse
}