package com.example.pokemon.data.repository

import com.example.pokemon.data.network.Pokemon
import com.example.pokemon.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository: Intermediario entre la capa de datos (API) y la capa de presentación (ViewModel)
 *
 * Responsabilidades:
 * - Obtener datos desde la API usando Retrofit
 * - Manejar errores de red
 * - Ejecutar operaciones en el hilo correcto (IO)
 * - Retornar Result<T> para facilitar el manejo de éxito/error
 */
class PokemonRepository {

    /**
     * Obtiene la lista de los primeros 100 Pokémon desde la PokéAPI
     *
     * @return Result<List<Pokemon>> - Éxito con la lista o Error con excepción
     */
    suspend fun getPokemonList(): Result<List<Pokemon>> {
        // withContext(Dispatchers.IO) asegura que la operación se ejecute en un hilo de I/O
        return withContext(Dispatchers.IO) {
            try {
                // Llamada a la API usando Retrofit
                val response = RetrofitClient.apiService.getPokemonList(limit = 100)

                // Si la llamada es exitosa, retorna Result.success con los datos
                Result.success(response.results)
            } catch (e: Exception) {
                // Si hay un error (red, timeout, JSON parsing, etc.), retorna Result.failure
                Result.failure(e)
            }
        }
    }

    /**
     * Método adicional para obtener un Pokémon específico por nombre
     * (Puede ser útil para futuras implementaciones)
     *
     * @param name Nombre del Pokémon
     * @return Result<Pokemon> - Éxito con el Pokémon o Error
     */
    suspend fun getPokemonByName(name: String): Result<Pokemon> {
        return withContext(Dispatchers.IO) {
            try {
                // Aquí podrías implementar una llamada específica a la API
                // Por ahora retornamos un ejemplo básico
                val response = RetrofitClient.apiService.getPokemonList(limit = 1)
                Result.success(response.results.first())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}