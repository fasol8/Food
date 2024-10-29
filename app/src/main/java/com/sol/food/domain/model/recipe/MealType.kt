package com.sol.food.domain.model.recipe

enum class MealType(val displayName: String) {
    MAIN_COURSE("Main Course"),
    SIDE_DISH("Side Dish"),
    DESSERT("Dessert"),
    APPETIZER("Appetizer"),
    SALAD("Salad"),
    BREAD("Bread"),
    BREAKFAST("Breakfast"),
    SOUP("Soup"),
    BEVERAGE("Beverage"),
    SAUCE("Sauce"),
    MARINADE("Marinade"),
    FINGERFOOD("Fingerfood"),
    SNACK("Snack"),
    DRINK("Drink");

    companion object {
        val size = CuisinesType.values().size
        operator fun get(index: Int) = CuisinesType.values()[index]
    }
}
