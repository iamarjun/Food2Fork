package com.arjun.food2fork.recipeList

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.arjun.food2fork.model.Recipe

class RecipeCategoryAdapter(
    private val list: List<Recipe>,
    private val imageLoader: ImageLoader,
    private val interaction: Interaction
) : RecyclerView.Adapter<RecipeCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeCategoryViewHolder {
        return RecipeCategoryViewHolder.create(parent, imageLoader, interaction)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecipeCategoryViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

}