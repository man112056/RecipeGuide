package com.sample.recipeguide

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

        val repository = RecipeRepository(this, ApiService.create())
        viewModel = ViewModelProvider(this, RecipeViewModel.Factory(repository)).get(RecipeViewModel::class.java)

        viewModel.fetchRecipeDetails(recipeId) // Fetch recipe details from API

        val titleTextView = findViewById<TextView>(R.id.textViewTitle)
        val summaryWebView = findViewById<WebView>(R.id.webViewSummary)
        val recipeImageView = findViewById<ImageView>(R.id.imageViewRecipe)

        // Configure WebView
        summaryWebView.settings.javaScriptEnabled = true
        summaryWebView.webViewClient = WebViewClient()

        viewModel.recipeDetail.observe(this) { recipe ->
            titleTextView.text = recipe.title

            // Load summary into WebView
            val htmlContent = "<html><body>${recipe.summary}</body></html>"
            summaryWebView.loadData(htmlContent, "text/html", "UTF-8")

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