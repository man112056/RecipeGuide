package com.sample.recipeguide

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.recipeguide.databinding.ActivityRecipeListBinding

class RecipeListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeListBinding
    private lateinit var viewModel: RecipeViewModel
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("CATEGORY_NAME") ?: "Main Course"

        // Create repository instance with ApiService
        val apiService = ApiService.create()  // Ensure ApiService has a `create()` method
        val repository = RecipeRepository(applicationContext, apiService)

        // Initialize ViewModel with factory
        val factory = RecipeViewModel.Factory(repository)
        viewModel = ViewModelProvider(this, factory)[RecipeViewModel::class.java]

        // Setup RecyclerView
        adapter = RecipeAdapter(emptyList()) { recipe ->
            viewModel.toggleFavorite(recipe)
        }
        binding.recyclerViewRecipes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRecipes.adapter = adapter

        // Show loader before fetching data
        binding.progressBar.visibility = View.VISIBLE

        // Observe LiveData for recipes
        viewModel.recipes.observe(this) { recipes ->
            binding.progressBar.visibility = View.GONE  // Hide loader when data is loaded
            adapter.updateRecipes(recipes)
        }

        // Observe error state
        viewModel.error.observe(this) { errorMessage ->
            binding.progressBar.visibility = View.GONE  // Hide loader on error
            // Show error message (Toast or Snackbar)
        }

        viewModel.fetchRecipes(category)
    }
}