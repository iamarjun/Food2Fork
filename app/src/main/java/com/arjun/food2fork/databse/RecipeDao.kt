package com.arjun.food2fork.databse

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arjun.food2fork.model.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeList(list: List<Recipe>)

    @Query("select * from recipe where lower(title) like :query order by socialRank desc")
    fun getRecipeList(query: String): DataSource.Factory<Int, Recipe>
}