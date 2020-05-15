package com.arjun.food2fork.repositories.inMemory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.model.network.NetworkRecipe

class RecipeDataSourceFactory(private val query: String, private val restApi: RestApi) :
    DataSource.Factory<Int, NetworkRecipe>() {

    private val source: MutableLiveData<RecipeDataSource> = MutableLiveData()

    val dataSource: LiveData<RecipeDataSource>
        get() = source

    override fun create(): DataSource<Int, NetworkRecipe> {
        val dataSource =
            RecipeDataSource(
                query,
                restApi
            )
        source.postValue(dataSource)
        return dataSource
    }

}