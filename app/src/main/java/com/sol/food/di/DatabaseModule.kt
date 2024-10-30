package com.sol.food.di

import android.content.Context
import androidx.room.Room
import com.sol.food.data.local.FoodDatabase
import com.sol.food.data.local.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): FoodDatabase {
        return Room.databaseBuilder(
            appContext,
            FoodDatabase::class.java,
            "food_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: FoodDatabase): ItemDao {
        return database.itemDao()
    }
}