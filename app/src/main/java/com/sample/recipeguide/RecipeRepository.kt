package com.sample.recipeguide


import android.content.Context

class RecipeRepository(context: Context, private val apiService: ApiService) {

    private val sharedPrefManager = SharedPrefManager(context)

    suspend fun getRecipes(query: String) = apiService.getRecipes(query)

    fun getFavoriteRecipes(): List<Int> = sharedPrefManager.getFavorites()

    fun addRecipeToFavorites(recipeId: Int) = sharedPrefManager.addFavorite(recipeId)

    fun removeRecipeFromFavorites(recipeId: Int) = sharedPrefManager.removeFavorite(recipeId)

    fun isRecipeFavorite(recipeId: Int) = sharedPrefManager.isFavorite(recipeId)
}
