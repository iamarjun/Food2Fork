package com.arjun.food2fork.recipeCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.arjun.food2fork.databinding.RecipeCategoryItemBinding
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.recipeList.Interaction

class RecipeCategoryViewHolder(
    private val binding: RecipeCategoryItemBinding,
    private val interaction: Interaction?
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: Recipe?) {
        with(binding) {
            item?.let { recipe ->
                root.setOnClickListener {
                    interaction?.onItemSelected(adapterPosition, recipe)
                }

                recipeCategoryImage.load(recipe.imageUrl) {
                    transformations(CircleCropTransformation())
                    crossfade(true)
                }


                recipeCategoryTitle.text = recipe.title
            }
        }

    }

    companion object {
        fun create(
            parent: ViewGroup,
            interaction: Interaction?
        ): RecipeCategoryViewHolder {
            return RecipeCategoryViewHolder(
                RecipeCategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                interaction
            )
        }
    }

}