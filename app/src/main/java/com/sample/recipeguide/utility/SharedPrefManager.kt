package com.sample.recipeguide.utility


import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

    fun saveFavorites(favorites: List<Int>) {
        val favStrings = favorites.map { it.toString() }.toSet()
        prefs.edit().putStringSet("favoriteIds", favStrings).apply()
    }

    fun getFavorites(): List<Int> {
        val favStrings = prefs.getStringSet("favoriteIds", emptySet()) ?: emptySet()
        return favStrings.map { it.toInt() }
    }

    fun addFavorite(recipeId: Int) {
        val favorites = getFavorites().toMutableList()
        if (!favorites.contains(recipeId)) {
            favorites.add(recipeId)
            saveFavorites(favorites)
        }
    }

    fun removeFavorite(recipeId: Int) {
        val favorites = getFavorites().toMutableList()
        favorites.remove(recipeId)
        saveFavorites(favorites)
    }

    fun isFavorite(recipeId: Int): Boolean {
        return getFavorites().contains(recipeId)
    }
}
