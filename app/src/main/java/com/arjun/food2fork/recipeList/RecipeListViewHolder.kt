package com.arjun.food2fork.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.arjun.food2fork.databinding.RecipeItemBinding
import com.arjun.food2fork.model.Recipe

class RecipeListViewHolder(
    private val binding: RecipeItemBinding,
    private val interaction: Interaction?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Recipe?) {
        with(binding) {
            item?.let { recipe ->
                root.setOnClickListener {
                    interaction?.onItemSelected(adapterPosition, recipe)
                }

                recipeImage.load(recipe.imageUrl) {
                    transformations(RoundedCornersTransformation(4f))
                    crossfade(true)
                }

                recipeTitle.text = recipe.title
                recipePublisher.text = recipe.publisher
                recipeSocialRating.text = recipe.socialRank.toString()
            }
        }
    }


    companion object {

        fun create(
            parent: ViewGroup,
            interaction: Interaction?
        ): RecipeListViewHolder {

            return RecipeListViewHolder(
                RecipeItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), interaction
            )
        }
    }
}