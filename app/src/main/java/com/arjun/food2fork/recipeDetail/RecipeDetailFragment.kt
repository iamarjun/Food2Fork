package com.arjun.food2fork.recipeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.arjun.food2fork.R
import com.arjun.food2fork.base.BaseFragment
import com.arjun.food2fork.databinding.FragmentRecipeDetailBinding
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.util.Resource
import com.arjun.food2fork.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RecipeDetailFragment : BaseFragment() {

    private val binding: FragmentRecipeDetailBinding by viewBinding(FragmentRecipeDetailBinding::bind)
    private val args: RecipeDetailFragmentArgs by navArgs()

    private val viewModel: RecipeDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        with(binding) {
            title.text = recipe?.title
            ingredients.text = recipe?.ingredients?.joinToString(separator = "\n")
            publisherName.text = "By: ${recipe?.publisher}"


            backdrop.load(recipe?.imageUrl) {
                transformations(RoundedCornersTransformation(4f))
                data(recipe?.imageUrl)
                crossfade(true)
            }

        }
    }


}