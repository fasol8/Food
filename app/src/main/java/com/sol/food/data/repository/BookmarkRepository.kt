package com.sol.food.data.repository

import com.sol.food.data.local.ItemDao
import com.sol.food.data.local.ItemEntity
import javax.inject.Inject

class BookmarkRepository @Inject constructor(private val itemDao: ItemDao) {

    suspend fun getAllItems(): List<ItemEntity> {
        return itemDao.getAllItems()
    }
}