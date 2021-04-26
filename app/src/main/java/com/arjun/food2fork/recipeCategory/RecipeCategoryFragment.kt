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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeCategoryFragment : BaseFragment() {

    private val binding: FragmentRecipeCategoryBinding by viewBinding(FragmentRecipeCategoryBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

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
                AppCompatDelegate.setDefaultNightMode(mode)
                false
            }


            recipeCategory.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                addItemDecoration(SpacingItemDecorator(0))
                adapter = RecipeCategoryAdapter(
                    Constants.getCategoryList(),
                    object : Interaction {
                        override fun onItemSelected(position: Int, item: Recipe) {
                            val action =
                                RecipeCategoryFragmentDirections.actionRecipeCategoryFragmentToRecipeListFragment(
                                    item.title
                                )

                            requireView().findNavController().navigate(action)
                        }
                    })
            }
        }

    }
}