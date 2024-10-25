package com.sol.food.domain.model.misc

enum class MiscType(val typeName: String) {
    Joke("Joke"),
    Trivia("Trivia");

    companion object {
        fun fromString(type: String): MiscType? = entries.find { it.typeName == type }
    }
}