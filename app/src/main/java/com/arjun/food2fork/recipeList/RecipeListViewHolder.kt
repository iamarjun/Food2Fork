package com.arjun.food2fork.recipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arjun.food2fork.R
import com.arjun.food2fork.model.Recipe

class RecipeListViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var interaction: Interaction? = null

    fun bind(item: Recipe?) {
        item?.let {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
        }
    }

    fun setInteractionListener(interaction: Interaction?) {
        this.interaction = interaction
    }

    companion object {

        fun create(parent: ViewGroup): NetworkStateViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
            return NetworkStateViewHolder(view)
        }
    }
}