package com.arjun.food2fork.databse

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.arjun.food2fork.model.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeList(list: List<Recipe>)

    @Update
    fun updateRecipe(recipe: Recipe)

    @Query("select * from recipe where recipeId like :recipeId")
    fun getRecipe(recipeId: String): Recipe

    @Query("select * from recipe where recipeId like :recipeId")
    fun getRecipeLiveData(recipeId: String): LiveData<Recipe>

    @Query("select * from recipe where lower(title) like :query order by socialRank desc")
    fun getRecipeList(query: String): DataSource.Factory<Int, Recipe>

    @Query("select count(*) from recipe where lower(title) like :query")
    fun getCount(query: String): Int
}