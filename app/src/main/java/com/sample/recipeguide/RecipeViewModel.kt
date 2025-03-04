package com.sample.recipeguide


import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class RecipeViewModel(context: Context, private val repository: RecipeRepository) : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    private val _favoriteIds = MutableLiveData<List<Int>>()
    val favoriteIds: LiveData<List<Int>> get() = _favoriteIds

    fun fetchRecipes(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.getRecipes(query)
                _recipes.postValue(response.results)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun loadFavorites() {
        _favoriteIds.postValue(repository.getFavoriteRecipes())
    }

    fun toggleFavorite(recipe: Recipe) {
        if (repository.isRecipeFavorite(recipe.id)) {
            repository.removeRecipeFromFavorites(recipe.id)
        } else {
            repository.addRecipeToFavorites(recipe.id)
        }
        loadFavorites()
    }
}