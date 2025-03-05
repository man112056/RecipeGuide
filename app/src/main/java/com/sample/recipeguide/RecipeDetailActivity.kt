package com.sample.recipeguide

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: RecipeViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId == -1) {
            finish() // Close activity if no valid ID
            return
        }

        val repository = RecipeRepository(this, ApiService.create())
        viewModel = ViewModelProvider(this, RecipeViewModel.Factory(repository)).get(RecipeViewModel::class.java)

        // UI elements
        val titleTextView = findViewById<TextView>(R.id.textViewTitle)
        val summaryWebView = findViewById<WebView>(R.id.webViewSummary)
        val recipeImageView = findViewById<ImageView>(R.id.imageViewRecipe)
        progressBar = findViewById(R.id.progressBar) // Reference ProgressBar

        // Show progress before fetching data
        progressBar.visibility = View.VISIBLE

        viewModel.fetchRecipeDetails(recipeId) // Call API to fetch details

        viewModel.recipeDetail.observe(this) { recipe ->
            progressBar.visibility = View.GONE // Hide progress once data is loaded

            titleTextView.text = recipe.title

            // Load summary in WebView
            summaryWebView.loadData(recipe.summary, "text/html", "UTF-8")

            // Load image using Glide
            Glide.with(this)
                .load(recipe.image)
                .into(recipeImageView)
        }

        viewModel.error.observe(this) { errorMsg ->
            progressBar.visibility = View.GONE // Hide progress on error
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }
}