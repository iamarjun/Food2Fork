package com.arjun.food2fork.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.arjun.food2fork.R
import com.arjun.food2fork.databinding.ReposLoadStateFooterViewItemBinding
import com.arjun.food2fork.util.toVisibility

class ReposLoadStateViewHolder(
    private val binding: ReposLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.visibility = toVisibility(loadState is LoadState.Loading)
        binding.retryButton.visibility = toVisibility(loadState !is LoadState.Loading)
        binding.errorMsg.visibility = toVisibility(loadState !is LoadState.Loading)
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ReposLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repos_load_state_footer_view_item, parent, false)
            val binding = ReposLoadStateFooterViewItemBinding.bind(view)
            return ReposLoadStateViewHolder(binding, retry)
        }
    }
}