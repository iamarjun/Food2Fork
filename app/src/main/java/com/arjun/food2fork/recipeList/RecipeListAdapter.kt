package com.arjun.food2fork.recipeList

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.arjun.food2fork.R
import com.arjun.food2fork.model.Recipe

class RecipeListAdapter(
    private val interaction: Interaction?
) :
    PagingDataAdapter<Recipe, RecyclerView.ViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeListViewHolder.create(parent, interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.recipe_item -> (holder as RecipeListViewHolder).bind(getItem(position))
        }
    }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = getItem(position)
        (holder as RecipeListViewHolder).bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.recipe_item
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Recipe>() {

            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }
        }
    }
}

