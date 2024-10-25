package com.sol.food.di

import com.sol.food.data.network.IngredientApi
import com.sol.food.data.network.MealPlanApi
import com.sol.food.data.network.MiscApi
import com.sol.food.data.network.ProductApi
import com.sol.food.data.network.RecipeApi
import com.sol.food.data.network.WineApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeApi(retrofit: Retrofit): RecipeApi {
        return retrofit.create(RecipeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMealPlanApi(retrofit: Retrofit): MealPlanApi {
        return retrofit.create(MealPlanApi::class.java)
    }

    @Provides
    @Singleton
    fun provideIngredientApi(retrofit: Retrofit): IngredientApi {
        return retrofit.create(IngredientApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWineApi(retrofit: Retrofit): WineApi {
        return retrofit.create(WineApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMiscApi(retrofit: Retrofit): MiscApi {
        return retrofit.create(MiscApi::class.java)
    }
}