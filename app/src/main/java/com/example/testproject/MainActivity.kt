package com.example.testproject

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.repository.data.PokemonDataItem
import com.example.testproject.ui.pokemonDetails.PokemonDetailsScreen
import com.example.testproject.ui.pokemonDetails.PokemonDetailsViewModel
import com.example.testproject.ui.pokemonList.ui.PokemonListScreen
import com.example.testproject.ui.theme.PokemonTheme
import com.google.gson.Gson
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
        val viewModel: PokemonDetailsViewModel = hiltViewModel()

        NavHost(
            navController = navController,
            startDestination = "list"
        ) {

            composable("list") {
                PokemonListScreen(navController)
            }
            composable(route = "details/{url}", arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )) { entry ->
                val url: String = entry.arguments?.getString("url")!!
                LaunchedEffect(url) {
                    viewModel.insertUrl(url)
                }
                PokemonDetailsScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}


class AssetParamType : NavType<PokemonDataItem>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PokemonDataItem? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): PokemonDataItem {
        return Gson().fromJson(value, PokemonDataItem::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: PokemonDataItem) {
        bundle.putParcelable(key, value)
    }
}
