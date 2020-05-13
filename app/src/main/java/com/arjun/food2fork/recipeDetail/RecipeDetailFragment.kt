package com.arjun.food2fork.recipeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.navArgs
import com.arjun.food2fork.R
import com.arjun.food2fork.base.BaseFragment
import com.arjun.food2fork.databinding.FragmentRecipeDetailBinding
import com.arjun.food2fork.util.viewBinding
import com.google.android.material.appbar.CollapsingToolbarLayout
import timber.log.Timber

class RecipeDetailFragment : BaseFragment() {

    private val binding: FragmentRecipeDetailBinding by viewBinding(FragmentRecipeDetailBinding::bind)
    private val args: RecipeDetailFragmentArgs by navArgs()

    private lateinit var tools: Toolbar
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var backdrop: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        controllerComponent.inject(this)
        super.onCreate(savedInstanceState)
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
    }


}