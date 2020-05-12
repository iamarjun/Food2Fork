package com.arjun.food2fork.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.arjun.food2fork.R
import com.arjun.food2fork.base.BaseFragment
import timber.log.Timber

class RecipeListFragment : BaseFragment() {

    private lateinit var viewModel: RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        controllerComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[RecipeListViewModel::class.java]
        viewModel.searchRecipe("chicken")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipeList.observe(viewLifecycleOwner) {
            it.forEach { recipe ->
                Timber.d(recipe.title)
            }
        }
        viewModel.networkState.observe(viewLifecycleOwner) {
            Timber.d(it.toString())
        }
        viewModel.refreshState.observe(viewLifecycleOwner) {
            Timber.d(it.toString())
        }
    }


}