package com.sample.recipeguide


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.recipeguide.databinding.ItemRecipeBinding


class RecipeListAdapter(private val viewModel: RecipeViewModel) :
    RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    private var recipes: List<Recipe> = listOf()

    fun submitList(newList: List<Recipe>) {
        recipes = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount() = recipes.size

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.textViewRecipeTitle.text = recipe.title
            Glide.with(binding.root).load(recipe.image).into(binding.imageViewRecipe)
            binding.buttonFavorite.setOnClickListener {
                viewModel.toggleFavorite(recipe)
            }
        }
    }
}
