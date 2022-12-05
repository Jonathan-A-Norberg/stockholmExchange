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
import com.example.testproject.ui.flickrDetails.FlickrDetailsScreen
import com.example.testproject.ui.flickrDetails.FlickrDetailsViewModel
import com.example.testproject.ui.flickrList.ui.FlickrListScreen
import com.example.testproject.ui.theme.FlickrTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setContent {
            val navController = rememberNavController()

            FlickrTheme {
                Scaffold {
                    NavigationComponent(navController)
                }
            }
        }

    }

    @Composable
    fun NavigationComponent(navController: NavHostController) {
        val viewModel: FlickrDetailsViewModel = hiltViewModel()

        NavHost(
            navController = navController,
            startDestination = "list"
        ) {

            composable("list") {
                FlickrListScreen(navController)
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
                FlickrDetailsScreen(navController = navController, viewModel = viewModel)

            }
        }
    }
}


