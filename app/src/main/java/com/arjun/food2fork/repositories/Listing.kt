package com.arjun.food2fork.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>, //initial state
    val refreshState: LiveData<NetworkState>, // second state, after first data loaded
    val refresh: () -> Unit, // signal the data source to stop loading, and notify its callback
    val retry: () -> Unit,  // remake the call
    val clearCoroutineJobs: () -> Unit // the way to stop jobs from running since no lifecycle provided
)