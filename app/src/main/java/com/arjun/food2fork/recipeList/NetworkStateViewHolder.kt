package com.arjun.food2fork.recipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arjun.food2fork.R
import com.arjun.food2fork.repositories.NetworkState
import com.arjun.food2fork.repositories.Status
import kotlinx.android.synthetic.main.network_state_item.view.*

class NetworkStateViewHolder internal constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private var errorMessage = itemView.error_msg
    private var loadingIndicator = itemView.loader
    private var retry = itemView.retry_button

    fun bind(networkState: NetworkState?) {

        networkState?.let {
            when (it.status) {
                Status.FAILED -> {
                    progressVisibility(false)
                    errorMessage?.text = "Try Again"
                    errorMessage?.visibility = View.VISIBLE
                    retry?.visibility = View.VISIBLE
                }
                Status.RUNNING -> {
                    progressVisibility(true)
                    errorMessage?.text = ""
                    errorMessage?.visibility = View.VISIBLE
                    retry?.visibility = View.GONE
                }
                else -> {
                    progressVisibility(false)
                    errorMessage?.text = ""
                    errorMessage?.visibility = View.GONE
                    retry?.visibility = View.GONE
                }
            }
        }

    }

    private fun progressVisibility(boolean: Boolean) {
        if (boolean) {
            loadingIndicator?.visibility = View.VISIBLE
        } else {
            loadingIndicator?.visibility = View.GONE
        }
    }

    companion object {

        fun create(parent: ViewGroup): NetworkStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.network_state_item, parent, false)
            return NetworkStateViewHolder(view)
        }
    }
}