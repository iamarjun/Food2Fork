package com.arjun.food2fork.databse

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.arjun.food2fork.model.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeList(list: List<Recipe>)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("select * from recipe where recipeId like '%'||:recipeId||'%'")
    suspend fun getRecipe(recipeId: String): Recipe

    @Query("select * from recipe where recipeId like '%'||:recipeId||'%'")
    fun getRecipeLiveData(recipeId: String): LiveData<Recipe>

    @Query("select * from recipe where lower(title) like '%'||:query||'%' order by socialRank desc")
     fun getRecipeList(query: String): PagingSource<Int, Recipe>

    @Query("select count(*) from recipe where lower(title) like '%'||:query||'%'")
    suspend fun getCount(query: String): Int

    @Query("DELETE FROM recipe")
    suspend fun clearRecipe()
}