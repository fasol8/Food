package com.sol.food.domain.model.recipe

enum class CuisinesType(val displayName: String) {
    African("African"),
    Asian("Asian"),
    American("American"),
    British("British"),
    Cajun("Cajun"),
    Caribbean("Caribbean"),
    Chinese("Chinese"),
    EasternEuropean("Eastern European"),
    European("European"),
    French("French"),
    German("German"),
    Greek("Greek"),
    Indian("Indian"),
    Irish("Irish"),
    Italian("Italian"),
    Japanese("Japanese"),
    Jewish("Jewish"),
    Korean("Korean"),
    LatinAmerican("Latin American"),
    Mediterranean("Mediterranean"),
    Mexican("Mexican"),
    MiddleEastern("Middle Eastern"),
    Nordic("Nordic"),
    Southern("Southern"),
    Spanish("Spanish"),
    Thai("Thai"),
    Vietnamese("Vietnamese");

    companion object {
        val size = values().size
        operator fun get(index: Int) = values()[index]
    }
}
