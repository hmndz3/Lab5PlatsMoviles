package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemon.network.Pokemon
import com.example.pokemon.ui.theme.DetailScreen
import com.example.pokemon.ui.theme.MainScreen
import com.example.pokemon.ui.theme.PokemonTheme

/**
 * Activity principal de la aplicación
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
 * Composable principal con navegación
 */
@Composable
fun PokemonApp() {
    val navController = rememberNavController()

    // Variable para almacenar el Pokémon seleccionado
    val selectedPokemon = remember { mutableListOf<Pokemon>() }

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        // Pantalla principal (lista de Pokémon)
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

        // Pantalla de detalle
        composable("detail") {
            if (selectedPokemon.isNotEmpty()) {
                DetailScreen(
                    pokemon = selectedPokemon[0],
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}