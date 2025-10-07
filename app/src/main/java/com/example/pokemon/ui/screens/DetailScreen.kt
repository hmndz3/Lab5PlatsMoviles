package com.example.pokemon.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokemon.data.network.Pokemon

/**
 * Pantalla de detalle que muestra las 4 imágenes del Pokémon
 * - Front normal
 * - Back normal
 * - Front shiny
 * - Back shiny
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    pokemon: Pokemon,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = pokemon.name.replaceFirstChar { it.uppercase() },
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3B4CCA),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Número del Pokémon
            Text(
                text = "#${pokemon.id.padStart(3, '0')}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3B4CCA)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre del Pokémon
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Título de sprites
            Text(
                text = "Sprites del Pokémon",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Grid 2x2 con las 4 imágenes
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Fila 1: Front Normal y Back Normal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SpriteCard(
                        imageUrl = pokemon.imageUrlFront,
                        label = "Frente",
                        subtitle = "Normal",
                        modifier = Modifier.weight(1f)
                    )

                    SpriteCard(
                        imageUrl = pokemon.imageUrlBack,
                        label = "Espalda",
                        subtitle = "Normal",
                        modifier = Modifier.weight(1f)
                    )
                }

                // Fila 2: Front Shiny y Back Shiny
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SpriteCard(
                        imageUrl = pokemon.imageUrlShinyFront,
                        label = "Frente",
                        subtitle = "Shiny ✨",
                        modifier = Modifier.weight(1f),
                        isShiny = true
                    )

                    SpriteCard(
                        imageUrl = pokemon.imageUrlShinyBack,
                        label = "Espalda",
                        subtitle = "Shiny ✨",
                        modifier = Modifier.weight(1f),
                        isShiny = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * Card para mostrar un sprite individual
 */
@Composable
fun SpriteCard(
    imageUrl: String,
    label: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    isShiny: Boolean = false
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isShiny) Color(0xFFFFF9C4) else Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Etiqueta superior
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = if (isShiny) Color(0xFFFF6F00) else Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Imagen del sprite
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "$label $subtitle",
                    modifier = Modifier.size(130.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                fontWeight = if (isShiny) FontWeight.Bold else FontWeight.Normal,
                color = if (isShiny) Color(0xFFFF6F00) else Color.Gray
            )
        }
    }
}