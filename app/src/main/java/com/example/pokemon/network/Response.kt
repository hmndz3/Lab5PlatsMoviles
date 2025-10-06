package com.example.pokemon.network

import com.google.gson.annotations.SerializedName

/**
 * Respuesta de la API que contiene la lista de Pokémon
 */
data class PokeResponse(
    @SerializedName("results")
    val results: List<Pokemon>
) {
    // Constructor vacío requerido por Firebase
    @Suppress("unused")
    constructor() : this(emptyList())
}

/**
 * Modelo básico de Pokémon
 * La API devuelve name y url, extraemos el ID de la URL
 */
data class Pokemon(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
) {
    // Constructor vacío requerido por Firebase
    @Suppress("unused")
    constructor() : this("", "")

    /**
     * Extrae el ID del Pokémon desde la URL
     * Ejemplo: "https://pokeapi.co/api/v2/pokemon/25/" -> "25"
     */
    val id: String
        get() {
            // Elimina el "/" final y divide por "/"
            val parts = url.trimEnd('/').split("/")
            // El ID es el último elemento
            return parts.lastOrNull() ?: "0"
        }

    /**
     * URL de la imagen frontal del Pokémon
     */
    val imageUrlFront: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

    /**
     * URL de la imagen trasera del Pokémon
     */
    val imageUrlBack: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png"

    /**
     * URL de la imagen frontal shiny del Pokémon
     */
    val imageUrlShinyFront: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png"

    /**
     * URL de la imagen trasera shiny del Pokémon
     */
    val imageUrlShinyBack: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/$id.png"
}