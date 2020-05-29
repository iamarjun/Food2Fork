package com.arjun.food2fork.recipeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import coil.request.LoadRequest
import coil.transform.RoundedCornersTransformation
import com.arjun.food2fork.R
import com.arjun.food2fork.base.BaseFragment
import com.arjun.food2fork.databinding.FragmentRecipeDetailBinding
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.util.Resource
import com.arjun.food2fork.util.viewBinding
import timber.log.Timber

class RecipeDetailFragment : BaseFragment() {

    private val binding: FragmentRecipeDetailBinding by viewBinding(FragmentRecipeDetailBinding::bind)
    private val args: RecipeDetailFragmentArgs by navArgs()

    private lateinit var viewModel: RecipeDetailViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var title: TextView
    private lateinit var publisherName: TextView
    private lateinit var backdrop: ImageView
    private lateinit var ingredients: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        controllerComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[RecipeDetailViewModel::class.java]
        viewModel.getRecipe(args.recipeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d(args.recipeId)

        toolbar = binding.toolbar
        title = binding.title
        backdrop = binding.backdrop
        publisherName = binding.publisherName
        ingredients = binding.recipeIngredients

        viewModel.recipe.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    showRecipe(it.data)
                }
                is Resource.Error -> {
                }
            }
        }

    }

    private fun showRecipe(recipe: Recipe?) {
        title.text = recipe?.title
        ingredients.text = recipe?.ingredients?.joinToString(separator = "\n")
        publisherName.text = "By: ${recipe?.publisher}"

        val request = LoadRequest.Builder(requireContext())
            .transformations(RoundedCornersTransformation(4f))
            .data(recipe?.imageUrl)
            .crossfade(true)
            .target(backdrop)
            .build()

        imageLoader.execute(request)
    }

}