package com.arjun.food2fork.recipeCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjun.food2fork.R
import com.arjun.food2fork.base.BaseFragment
import com.arjun.food2fork.databinding.FragmentRecipeCategoryBinding
import com.arjun.food2fork.model.network.NetworkRecipe
import com.arjun.food2fork.recipeList.Interaction
import com.arjun.food2fork.util.Constants
import com.arjun.food2fork.util.SpacingItemDecorator
import com.arjun.food2fork.util.viewBinding

class RecipeCategoryFragment : BaseFragment() {

    private val binding: FragmentRecipeCategoryBinding by viewBinding(FragmentRecipeCategoryBinding::bind)

    private lateinit var recipeCategoryAdapter: RecipeCategoryAdapter
    private lateinit var recipeCategory: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        controllerComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeCategory = binding.recipeCategory

        recipeCategoryAdapter =
            RecipeCategoryAdapter(
                Constants.getCategoryList(), imageLoader,
                object : Interaction {
                    override fun onItemSelected(position: Int, item: NetworkRecipe) {
                        val action =
                            RecipeCategoryFragmentDirections.actionRecipeCategoryFragmentToRecipeListFragment(
                                item.title!!
                            )

                        requireView().findNavController().navigate(action)
                    }
                })

        recipeCategory.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(SpacingItemDecorator(12))
            adapter = recipeCategoryAdapter
        }

    }
}