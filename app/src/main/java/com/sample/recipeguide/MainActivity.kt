package com.sample.recipeguide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.recipeguide.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RecipeViewModel
    private lateinit var adapter: RecipeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = RetrofitClient.create(ApiService::class.java)
        val repository = RecipeRepository(this, apiService)
        viewModel = RecipeViewModel(this, repository)

        adapter = RecipeListAdapter(viewModel)
        binding.recyclerViewRecipes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRecipes.adapter = adapter

        viewModel.recipes.observe(this) { recipes ->
            adapter.submitList(recipes)
        }

        viewModel.favoriteIds.observe(this) {
            adapter.notifyDataSetChanged()
        }

        binding.buttonViewFavorites.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            viewModel.fetchRecipes(query)
        }

        viewModel.loadFavorites()
    }
}