package com.sample.recipeguide

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Fetch list of recipes based on a search query
    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = "b4e9c600cef645e28fb40b3e106a2115"
    ): RecipeResponse

    // Fetch detailed information for a specific recipe by ID
    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") recipeId: Int,
        @Query("apiKey") apiKey: String = "b4e9c600cef645e28fb40b3e106a2115"
    ): Recipe

    companion object {
        private const val BASE_URL = "https://api.spoonacular.com/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}