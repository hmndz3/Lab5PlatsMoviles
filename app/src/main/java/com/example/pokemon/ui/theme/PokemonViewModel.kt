package com.example.pokemon.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.network.Pokemon
import com.example.pokemon.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estados posibles de la UI
 */
sealed class UiState {
    data object Loading : UiState()
    data class Success(val pokemonList: List<Pokemon>) : UiState()
    data class Error(val message: String) : UiState()
}

/**
 * ViewModel que maneja la lógica de obtener los Pokémon
 */
class PokemonViewModel : ViewModel() {

    // Estado privado mutable
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    // Estado público inmutable para observar desde la UI
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        // Cargar lista al iniciar
        loadPokemonList()
    }

    /**
     * Carga la lista de los primeros 100 Pokémon
     */
    fun loadPokemonList() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val response = RetrofitClient.apiService.getPokemonList(limit = 100)
                _uiState.value = UiState.Success(response.results)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.message ?: "Error desconocido al cargar los Pokémon"
                )
            }
        }
    }
}