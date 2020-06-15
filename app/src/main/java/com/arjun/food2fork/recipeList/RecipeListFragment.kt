package com.arjun.food2fork.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjun.food2fork.R
import com.arjun.food2fork.base.BaseFragment
import com.arjun.food2fork.databinding.FragmentRecipeListBinding
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.util.SpacingItemDecorator
import com.arjun.food2fork.util.toVisibility
import com.arjun.food2fork.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RecipeListFragment : BaseFragment() {

    private val binding: FragmentRecipeListBinding by viewBinding(FragmentRecipeListBinding::bind)
    private val args: RecipeListFragmentArgs by navArgs()

    private val viewModel: RecipeListViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeListAdapter
    private lateinit var recipeList: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var retry: Button

    private var searchJob: Job? = null

    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        retry = binding.retryButton

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { search(it) }
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

        recipeAdapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { recipeAdapter.retry() },
            footer = ReposLoadStateAdapter { recipeAdapter.retry() }
        )

        recipeAdapter.addLoadStateListener {loadState ->
            if (loadState.refresh !is LoadState.NotLoading) {
                // We're refreshing: either loading or we had an error
                // So we can hide the list
                binding.recipeList.visibility = View.GONE
                binding.progressBar.visibility = toVisibility(loadState.refresh is LoadState.Loading)
                binding.retryButton.visibility = toVisibility(loadState.refresh is LoadState.Error)
            } else {
                // We're not actively refreshing
                // So we should show the list
                binding.recipeList.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> {
                        loadState.append as LoadState.Error
                    }
                    loadState.prepend is LoadState.Error -> {
                        loadState.prepend as LoadState.Error
                    }
                    else -> {
                        null
                    }
                }
                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        retry.setOnClickListener { recipeAdapter.retry() }

        search(args.searchQuery)

        recipeList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(SpacingItemDecorator(12))
            adapter = recipeAdapter
        }


    }

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRecipe(query).collectLatest {
                recipeAdapter.submitData(it)
            }
        }
    }

}