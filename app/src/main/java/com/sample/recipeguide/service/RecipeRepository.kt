package com.sample.recipeguide.service


import android.content.Context
import com.sample.recipeguide.utility.SharedPrefManager
import com.sample.recipeguide.model.Recipe

class RecipeRepository(context: Context, private val apiService: ApiService) {

    private val sharedPrefManager = SharedPrefManager(context)

    suspend fun getRecipes(query: String) = apiService.getRecipes(query)

    suspend fun getRecipeDetails(recipeId: Int): Recipe {
        return apiService.getRecipeDetails(recipeId)
    }

    fun getFavoriteRecipes(): List<Int> = sharedPrefManager.getFavorites()

    fun addRecipeToFavorites(recipeId: Int) = sharedPrefManager.addFavorite(recipeId)

    fun removeRecipeFromFavorites(recipeId: Int) = sharedPrefManager.removeFavorite(recipeId)

    fun isRecipeFavorite(recipeId: Int) = sharedPrefManager.isFavorite(recipeId)
}
