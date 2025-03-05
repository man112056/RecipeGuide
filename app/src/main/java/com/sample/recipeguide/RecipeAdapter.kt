package com.sample.recipeguide

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.recipeguide.databinding.ItemRecipeBinding

class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onFavoriteClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.textViewRecipeTitle.text = recipe.title

            // Load image using Glide
            Glide.with(binding.imageViewRecipe.context)
                .load(recipe.image)
                .into(binding.imageViewRecipe)

            // Favorite button toggle
//            binding.buttonFavorite.setImageResource(
//                if (recipe.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
//            )

            // Favorite button click listener
            binding.buttonFavorite.setOnClickListener {
                onFavoriteClick(recipe)
            }

            // Click listener to open details screen
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, RecipeDetailActivity::class.java).apply {
                    putExtra("RECIPE_ID", recipe.id)
                    putExtra("RECIPE_TITLE", recipe.title)
                    putExtra("RECIPE_IMAGE", recipe.image)
                    putExtra("RECIPE_SUMMARY", recipe.summary)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding =
            ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}