package com.example.testproject

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testproject.ui.pokemonList.ui.PokemonListScreen
import com.example.testproject.ui.pokemonList.ui.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setContent {
            val navController = rememberNavController()

            PokemonTheme {
                Scaffold {
                    NavigationComponent(navController)
                }
            }
        }

    }

    @Composable
    fun NavigationComponent(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = "list"
        ) {
            composable("list") {
                PokemonListScreen(navController)
            }
        }
    }
}
