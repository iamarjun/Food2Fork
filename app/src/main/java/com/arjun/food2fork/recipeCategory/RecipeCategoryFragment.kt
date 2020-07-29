package com.arjun.food2fork.recipeCategory

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjun.food2fork.R
import com.arjun.food2fork.base.BaseFragment
import com.arjun.food2fork.databinding.FragmentRecipeCategoryBinding
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.recipeList.Interaction
import com.arjun.food2fork.util.Constants
import com.arjun.food2fork.util.SpacingItemDecorator
import com.arjun.food2fork.util.viewBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeCategoryFragment : BaseFragment() {

    private val binding: FragmentRecipeCategoryBinding by viewBinding(FragmentRecipeCategoryBinding::bind)

    private lateinit var recipeCategoryAdapter: RecipeCategoryAdapter
    private lateinit var recipeCategory: RecyclerView
    private lateinit var toolbar: MaterialToolbar

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
        toolbar = binding.toolbar

        toolbar.setOnMenuItemClickListener {
            val mode =
                if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                    Configuration.UI_MODE_NIGHT_NO
                ) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                }

            // Change UI Mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            false
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        recipeCategoryAdapter =
            RecipeCategoryAdapter(
                Constants.getCategoryList(), imageLoader,
                object : Interaction {
                    override fun onItemSelected(position: Int, item: Recipe) {
                        val action =
                            RecipeCategoryFragmentDirections.actionRecipeCategoryFragmentToRecipeListFragment(
                                item.title
                            )

                        requireView().findNavController().navigate(action)
                    }
                })

        recipeCategory.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(SpacingItemDecorator(0))
            adapter = recipeCategoryAdapter
        }

    }
}