package com.arjun.food2fork.recipeCategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation
import com.arjun.food2fork.R
import com.arjun.food2fork.model.network.NetworkRecipe
import com.arjun.food2fork.recipeList.Interaction
import kotlinx.android.synthetic.main.recipe_category_item.view.*

class RecipeCategoryViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val interaction: Interaction?
) : RecyclerView.ViewHolder(itemView) {

    private var title: AppCompatTextView = itemView.recipe_category_title
    private var image: AppCompatImageView = itemView.recipe_category_image

    fun bind(item: NetworkRecipe?) {
        item?.let {recipe ->
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, recipe)
            }

            val request = LoadRequest.Builder(itemView.context)
                .transformations(CircleCropTransformation())
                .data(recipe.imageUrl)
                .crossfade(true)
                .target(image)
                .build()

            imageLoader.execute(request)

            title.text = recipe.title
        }
    }

    fun unbind() {
        imageLoader.shutdown()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            imageLoader: ImageLoader,
            interaction: Interaction?
        ): RecipeCategoryViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recipe_category_item, parent, false)
            return RecipeCategoryViewHolder(
                view,
                imageLoader,
                interaction
            )
        }
    }

}