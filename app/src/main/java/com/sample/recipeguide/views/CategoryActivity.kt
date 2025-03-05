package com.sample.recipeguide.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.sample.recipeguide.model.Category
import com.sample.recipeguide.adapter.CategoryAdapter
import com.sample.recipeguide.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categories: List<Category> = listOf(
            Category("Main Course"),
            Category("Side Dish"),
            Category("Dessert"),
            Category("Appetizer"),
            Category("Salad"),
            Category("Bread"),
            Category("Breakfast"),
            Category("Soup"),
            Category("Beverage"),
            Category("Sauce"),
            Category("Marinade"),
            Category("Finger Food"),
            Category("Snack"),
            Category("Drink")
        )

        val adapter = CategoryAdapter(categories) { selectedCategory ->
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("CATEGORY_NAME", selectedCategory.name)
            startActivity(intent)
        }

        // Use GridLayoutManager for a grid-style display
        binding.recyclerViewCategories.layoutManager = GridLayoutManager(this, 2) // 2 columns
        binding.recyclerViewCategories.adapter = adapter
    }
}