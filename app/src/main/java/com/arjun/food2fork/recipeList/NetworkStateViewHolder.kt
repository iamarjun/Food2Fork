package com.arjun.food2fork.recipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arjun.food2fork.R
import com.arjun.food2fork.repositories.NetworkState
import com.arjun.food2fork.repositories.Status
import kotlinx.android.synthetic.main.network_state_item.view.*

class NetworkStateViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var errorMessage = itemView.error_msg
    private var loadingIndicator = itemView.progress_bar

    fun bind(networkState: NetworkState?) {

        networkState?.let {
            when (it.status) {
                Status.FAILED -> {
                    loadingIndicator?.visibility = View.GONE
                    errorMessage?.text = "Try Again"
                    errorMessage?.visibility = View.VISIBLE
                }
                Status.RUNNING -> {
                    loadingIndicator?.visibility = View.VISIBLE
                    errorMessage?.visibility = View.VISIBLE
                }
                else -> errorMessage?.visibility = View.GONE
            }
        }

    }

    companion object {

        fun create(parent: ViewGroup): NetworkStateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
            return NetworkStateViewHolder(view)
        }
    }
}