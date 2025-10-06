package com.example.pokemon.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz que define los endpoints de la PokéAPI
 */
interface PokeApiService {

    /**
     * Obtiene la lista de Pokémon con paginación
     * @param limit Cantidad de Pokémon a obtener (por defecto 100)
     * @param offset Desde qué posición empezar (por defecto 0)
     */
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): PokeResponse
}