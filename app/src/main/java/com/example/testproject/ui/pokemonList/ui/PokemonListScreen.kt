package com.example.testproject.ui.pokemonList.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.example.repository.data.PokemonDataItem
import com.example.testproject.ui.pokemonList.PokemonListViewModel
import com.example.testproject.ui.views.ErrorScreen
import com.example.testproject.ui.views.LoadingScreen
import timber.log.Timber
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun PokemonListScreen(
    navController: NavHostController,
    viewModel: PokemonListViewModel = hiltViewModel(),

    ) {
    val viewState by viewModel.state.collectAsState()


    var pokemonDataItem: PokemonDataItem? by remember { mutableStateOf(null) }
    if (pokemonDataItem != null) {
        val encodedUrl = URLEncoder.encode(pokemonDataItem!!.url, StandardCharsets.UTF_8.toString())
        navController.navigate("details/${encodedUrl}")
        pokemonDataItem = null
    }
    when {
        viewState.loading -> LoadingScreen()
        viewState.error != null -> ErrorScreen(
            error = viewState.error!!,
            onTryAgainClicked = { viewModel.onTryAgainClicked() })
        else -> {
            PokemonList(
                list = viewState.pokemonList,
                loadingPaging = viewState.loadingPaging,
                onPokemonClicked = { pokemonDataItem = it },
                onScrolledToBottom = { viewModel.onScrolledToBottom() })

        }
    }
}




@Composable
private fun PokemonList(
    list: List<PokemonDataItem>,
    loadingPaging: Boolean,
    onPokemonClicked: (PokemonDataItem) -> Unit,
    onScrolledToBottom: () -> Unit
) {

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .componentRegistry {
            add(SvgDecoder(LocalContext.current))
        }
        .build()


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        LazyColumn(
            modifier = Modifier,
        ) {
            itemsIndexed(list) { index, pokemon ->
                if (list.size - 1 == index) {
                    onScrolledToBottom()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(horizontal = 8.dp)
                        .shadow(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = MaterialTheme.colors.onBackground)
                        .clickable {
                            onPokemonClicked(pokemon)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (pokemon.id == null) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = pokemon.id!!,
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.onSurface
                        )

                    }
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = pokemon.name,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                        val painter = rememberImagePainter(pokemon.image)
                        Image(
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 8.dp)
                                .padding(vertical = 8.dp),
                            painter = painter,
                            contentDescription = pokemon.name,
                            contentScale = ContentScale.Fit
                        )
                    }
                }


            }

        }
        if (loadingPaging) {

            CircularProgressIndicator()
        }
    }

}
