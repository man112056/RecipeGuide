package com.sample.recipeguide

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId == -1) {
            finish() // Close activity if no valid ID
            return
        }

        val repository = RecipeRepository(this
                , ApiService.create())
        viewModel = ViewModelProvider(this, RecipeViewModel.Factory(repository)).get(RecipeViewModel::class.java)

        viewModel.fetchRecipeDetails(recipeId) // Call API to fetch details

        val titleTextView = findViewById<TextView>(R.id.textViewTitle)
        val summaryTextView = findViewById<TextView>(R.id.textViewSummary)
        val recipeImageView = findViewById<ImageView>(R.id.imageViewRecipe)

        viewModel.recipeDetail.observe(this) { recipe ->
            titleTextView.text = recipe.title
            summaryTextView.text = recipe.summary

            // Load image using Glide
            Glide.with(this)
                .load(recipe.image)
                .into(recipeImageView)
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }
}