package com.sample.recipeguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
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
            Category("Dessert")
        )

        val adapter = CategoryAdapter(categories) { selectedCategory ->
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("CATEGORY_NAME", selectedCategory.name)
            startActivity(intent)
        }

        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCategories.adapter = adapter
    }
}
