package com.sol.food.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemEntity::class], version = 3)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}