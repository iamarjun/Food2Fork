package com.arjun.food2fork.recipeList

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.arjun.food2fork.R
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.repositories.NetworkState

class RecipeListAdapter(
    private val imageLoader: ImageLoader,
    private val interaction: Interaction?
) :
    PagingDataAdapter<Recipe, RecyclerView.ViewHolder>(diffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeListViewHolder.create(parent, imageLoader, interaction)
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

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState !== NetworkState.LOADED
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

