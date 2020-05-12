package com.arjun.food2fork.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.arjun.food2fork.R
import com.arjun.food2fork.base.BaseFragment
import com.arjun.food2fork.databinding.FragmentRecipeListBinding
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.util.SpacingItemDecorator
import com.arjun.food2fork.util.viewBinding
import timber.log.Timber
import javax.inject.Inject

class RecipeListFragment : BaseFragment() {

    @Inject
    internal lateinit var imageLoader: ImageLoader

    private val binding: FragmentRecipeListBinding by viewBinding(FragmentRecipeListBinding::bind)


    private lateinit var viewModel: RecipeListViewModel
    private lateinit var recipeAdapter: RecipeListAdapter
    private lateinit var recipeList: RecyclerView

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

        recipeList = binding.recipeList

        recipeAdapter = RecipeListAdapter(imageLoader, object: Interaction {
            override fun onItemSelected(position: Int, item: Recipe) {
                Timber.d(item.title)
            }
        })

        recipeList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(SpacingItemDecorator(12))
            adapter = recipeAdapter
        }

        viewModel.recipeList.observe(viewLifecycleOwner) {
            recipeAdapter.submitList(it)
        }
        viewModel.networkState.observe(viewLifecycleOwner) {
            recipeAdapter.setNetworkState(it)
        }
        viewModel.refreshState.observe(viewLifecycleOwner) {
            recipeAdapter.setNetworkState(it)
        }
    }


}