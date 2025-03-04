package com.sample.recipeguide

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = "b4e9c600cef645e28fb40b3e106a2115"
    ): RecipeResponse

    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/") // Replace with actual API
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}