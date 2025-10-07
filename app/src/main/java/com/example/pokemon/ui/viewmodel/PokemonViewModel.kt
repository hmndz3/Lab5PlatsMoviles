package com.example.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.network.Pokemon
import com.example.pokemon.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estados posibles de la UI
 *
 * Sealed class que representa todos los estados posibles de la pantalla:
 * - Loading: Cargando datos
 * - Success: Datos cargados exitosamente
 * - Error: Ocurrió un error
 */
sealed class UiState {
    data object Loading : UiState()
    data class Success(val pokemonList: List<Pokemon>) : UiState()
    data class Error(val message: String) : UiState()
}

/**
 * ViewModel: Maneja la lógica de presentación y el estado de la UI
 *
 * Responsabilidades:
 * - Exponer el estado de la UI mediante StateFlow
 * - Comunicarse con el Repository para obtener datos
 * - Transformar errores del Repository en mensajes para la UI
 * - Manejar el ciclo de vida de las coroutines con viewModelScope
 *
 * Mejoras vs versión anterior:
 * - Ya no llama directamente a RetrofitClient (mejor separación de responsabilidades)
 * - Usa Repository como intermediario
 * - Más fácil de testear (se puede mockear el Repository)
 */
class PokemonViewModel(
    private val repository: PokemonRepository = PokemonRepository()
) : ViewModel() {

    // Estado privado mutable (solo el ViewModel puede modificarlo)
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)

    // Estado público inmutable (la UI solo puede observarlo, no modificarlo)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        // Cargar la lista automáticamente al crear el ViewModel
        loadPokemonList()
    }

    /**
     * Carga la lista de los primeros 100 Pokémon
     *
     * Flujo:
     * 1. Establece el estado en Loading
     * 2. Llama al Repository para obtener los datos
     * 3. Si es exitoso, actualiza el estado a Success con los datos
     * 4. Si falla, actualiza el estado a Error con el mensaje
     */
    fun loadPokemonList() {
        // viewModelScope.launch crea una coroutine atada al ciclo de vida del ViewModel
        viewModelScope.launch {
            // Cambiar estado a Loading
            _uiState.value = UiState.Loading

            // Llamar al Repository
            val result = repository.getPokemonList()

            // Procesar el resultado usando Result.onSuccess y Result.onFailure
            result
                .onSuccess { pokemonList ->
                    // Si fue exitoso, actualizar el estado con los datos
                    _uiState.value = UiState.Success(pokemonList)
                }
                .onFailure { exception ->
                    // Si falló, actualizar el estado con el mensaje de error
                    _uiState.value = UiState.Error(
                        exception.message ?: "Error desconocido al cargar los Pokémon"
                    )
                }
        }
    }

    /**
     * Reinicia el estado a Loading
     * Útil cuando se navega de regreso desde otra pantalla
     */
    fun resetState() {
        _uiState.value = UiState.Loading
    }
}