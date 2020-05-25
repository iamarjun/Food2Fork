package com.arjun.food2fork.databse

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.model.RecipePref

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipePref(recipePref: RecipePref)

    @Query("select pageNo from recipepref where recipeName like '%'||:recipeName||'%'")
    suspend fun getRecipePageNo(recipeName: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeList(list: List<Recipe>)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("select * from recipe where recipeId like '%'||:recipeId||'%'")
    suspend fun getRecipe(recipeId: String): Recipe

    @Query("select * from recipe where recipeId like '%'||:recipeId||'%'")
    fun getRecipeLiveData(recipeId: String): LiveData<Recipe>

    @Query("select * from recipe where lower(title) like '%'||:query||'%' order by socialRank desc")
    fun getRecipeList(query: String): DataSource.Factory<Int, Recipe>

    @Query("select count(*) from recipe where lower(title) like '%'||:query||'%'")
    suspend fun getCount(query: String): Int
}