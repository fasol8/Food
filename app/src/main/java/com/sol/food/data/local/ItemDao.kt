package com.sol.food.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<ItemEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM items WHERE id=:itemId)")
    suspend fun isItemSaved(itemId: Int): Boolean

    @Query("SELECT * FROM items WHERE id=:itemId LIMIT 1")
    suspend fun getItemById(itemId: Int): ItemEntity
}