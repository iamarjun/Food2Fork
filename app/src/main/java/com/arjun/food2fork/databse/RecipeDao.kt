package com.arjun.food2fork.databse

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arjun.food2fork.model.network.NetworkRecipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeList(list: List<NetworkRecipe>)

    @Query("select * from recipe")
    fun getRecipeList(): DataSource.Factory<Int, NetworkRecipe>
}