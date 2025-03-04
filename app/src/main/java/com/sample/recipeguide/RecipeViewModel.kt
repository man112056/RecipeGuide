package com.sample.recipeguide

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    private val _favoriteIds = MutableLiveData<List<Int>>()
    val favoriteIds: LiveData<List<Int>> get() = _favoriteIds

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchRecipes(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.getRecipes(query)
                _recipes.postValue(response.results)
            } catch (e: Exception) {
                _error.postValue("Failed to load recipes: ${e.message}")
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favoriteIds.postValue(repository.getFavoriteRecipes())
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            if (repository.isRecipeFavorite(recipe.id)) {
                repository.removeRecipeFromFavorites(recipe.id)
            } else {
                repository.addRecipeToFavorites(recipe.id)
            }
            loadFavorites()  // Refresh favorite list
        }
    }

    // Factory class inside ViewModel
    class Factory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecipeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
