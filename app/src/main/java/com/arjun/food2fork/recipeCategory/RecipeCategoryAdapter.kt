package com.arjun.food2fork.recipeCategory

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.recipeList.Interaction

class RecipeCategoryAdapter(
    private val list: List<Recipe>,
    private val interaction: Interaction
) : RecyclerView.Adapter<RecipeCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeCategoryViewHolder {
        return RecipeCategoryViewHolder.create(
            parent,
            interaction
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecipeCategoryViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

}