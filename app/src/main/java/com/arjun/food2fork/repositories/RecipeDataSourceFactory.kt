package com.arjun.food2fork.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.model.Recipe

class RecipeDataSourceFactory(private val query: String, private val restApi: RestApi) :
    DataSource.Factory<Int, Recipe>() {

    private val source: MutableLiveData<RecipeDataSource> = MutableLiveData()

    val dataSource: LiveData<RecipeDataSource>
        get() = source

    override fun create(): DataSource<Int, Recipe> {
        val dataSource = RecipeDataSource(query, restApi)
        source.postValue(dataSource)
        return dataSource
    }

}