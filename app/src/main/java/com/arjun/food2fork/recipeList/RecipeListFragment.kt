package com.arjun.food2fork.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjun.food2fork.R
import com.arjun.food2fork.base.BaseFragment
import com.arjun.food2fork.databinding.FragmentRecipeListBinding
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.util.SpacingItemDecorator
import com.arjun.food2fork.util.viewBinding
import timber.log.Timber

class RecipeListFragment : BaseFragment() {

    private val binding: FragmentRecipeListBinding by viewBinding(FragmentRecipeListBinding::bind)
    private val args: RecipeListFragmentArgs by navArgs()

    private lateinit var viewModel: RecipeListViewModel
    private lateinit var recipeAdapter: RecipeListAdapter
    private lateinit var recipeList: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        controllerComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[RecipeListViewModel::class.java]
        viewModel.searchRecipe(args.searchQuery)
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
        searchView = binding.search

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchRecipe(it) }
                return false
            }
        })

        recipeAdapter = RecipeListAdapter(imageLoader, object : Interaction {
            override fun onItemSelected(position: Int, item: Recipe) {
                Timber.d("${item.title} at $position")
                val action =
                    RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(item.recipeId)
                requireView().findNavController().navigate(action)
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

        /**
         * Custom back press behaviour
         */

//        callback = object : OnBackPressedCallback(false) {
//            override fun handleOnBackPressed() {
//                recipeList.adapter = recipeCategoryAdapter
//                isEnabled = false
//            }
//        }
//
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

}