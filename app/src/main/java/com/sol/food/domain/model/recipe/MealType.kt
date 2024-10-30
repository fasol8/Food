package com.sol.food.domain.model.recipe

import com.sol.food.R

enum class MealType(val displayName: String, val icon: Int) {
    MAIN_COURSE("Main Course", R.drawable.maincourse),
    SIDE_DISH("Side Dish", R.drawable.sidedish),
    DESSERT("Dessert", R.drawable.dessert),
    APPETIZER("Appetizer", R.drawable.appetizer),
    SALAD("Salad", R.drawable.salad),
    BREAD("Bread", R.drawable.bread),
    BREAKFAST("Breakfast", R.drawable.breakfast),
    SOUP("Soup", R.drawable.soup),
    BEVERAGE("Beverage", R.drawable.beverage),
    SAUCE("Sauce", R.drawable.sauce),
    MARINADE("Marinade", R.drawable.marinade),
    FINGERFOOD("Fingerfood", R.drawable.fingerfood),
    SNACK("Snack", R.drawable.snack),
    DRINK("Drink", R.drawable.drink);

    companion object {
        val size = CuisinesType.values().size
        operator fun get(index: Int) = MealType.values()[index]
    }
}
