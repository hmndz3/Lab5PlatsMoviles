package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemon.data.network.Pokemon
import com.example.pokemon.ui.screens.DetailScreen
import com.example.pokemon.ui.screens.MainScreen
import com.example.pokemon.ui.theme.PokemonTheme

/**
 * MainActivity: Punto de entrada de la aplicación
 *
 * Responsabilidades:
 * - Configurar el tema de la aplicación
 * - Inicializar el sistema de navegación
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonTheme {
                PokemonApp()
            }
        }
    }
}

/**
 * Composable principal que configura la navegación de la aplicación
 *
 * Navegación:
 * - "main": Pantalla principal con la lista de Pokémon
 * - "detail": Pantalla de detalle de un Pokémon seleccionado
 */
@Composable
fun PokemonApp() {
    val navController = rememberNavController()

    // Variable para almacenar temporalmente el Pokémon seleccionado
    // (En una app más compleja, esto se manejaría con argumentos de navegación)
    val selectedPokemon = remember { mutableListOf<Pokemon>() }

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        // Ruta: Pantalla principal
        composable("main") {
            MainScreen(
                onPokemonClick = { pokemon ->
                    // Guardar el Pokémon seleccionado
                    selectedPokemon.clear()
                    selectedPokemon.add(pokemon)
                    // Navegar a la pantalla de detalle
                    navController.navigate("detail")
                }
            )
        }

        // Ruta: Pantalla de detalle
        composable("detail") {
            if (selectedPokemon.isNotEmpty()) {
                DetailScreen(
                    pokemon = selectedPokemon[0],
                    onBackClick = {
                        // Navegar hacia atrás
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}