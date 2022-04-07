package com.example.testproject.ui.pokemonDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.example.repository.data.PokemonDataItem
import com.example.repository.data.PokemonDataItemStats
import com.example.testproject.R

@Composable
fun PokemonDetailsScreen(
    navController: NavHostController,
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    pokemon: PokemonDataItem,
) {
    val viewState by viewModel.state.collectAsState()



    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        PokemonCardView(pokemon)

        PokemonStatsListView(pokemon)
        PokemonDimensionsItemView(pokemon)
    }

}

@Composable
private fun PokemonCardView(pokemon: PokemonDataItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.onBackground),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PokemonName(pokemon)
            Image(pokemon)
            PokemonTypesView(pokemon)
        }

    }
}

@Composable
private fun PokemonStatsListView(pokemon: PokemonDataItem) {
    CardView() {
        Column {
            for (item in pokemon.stats) {
                PokemonStatsItemView(item)
            }
        }
    }
}

@Composable
private fun CardView(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.onBackground)
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        content()
    }
}

@Composable
private fun PokemonName(pokemon: PokemonDataItem) {
    Text(
        text = "${pokemon.id}. ${pokemon.name}",
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.onSurface
    )
}

@Composable
private fun Image(pokemon: PokemonDataItem) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .componentRegistry {
            add(SvgDecoder(LocalContext.current))
        }
        .build()
    CompositionLocalProvider(LocalImageLoader provides imageLoader) {
        val painter = rememberImagePainter(pokemon.image)
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp),
            painter = painter,
            contentDescription = pokemon.name,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun PokemonTypesView(pokemon: PokemonDataItem) {
    LazyRow() {
        items(pokemon.types) { item ->
            PokemonTypesItemView(item)
        }
    }
}

@Composable
private fun PokemonStatsItemView(stats: PokemonDataItemStats) {
    val progress = (stats.stat.toFloat() / 255)
    Column(modifier = Modifier.padding(top = 12.dp)) {
        Text(
            modifier = Modifier,
            text = "${stats.name}: ${stats.stat}",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface
        )
        LinearProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = progress,
            color = getProgressColor(progress = progress),
            backgroundColor = Color.White.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
        )
    }
}

@Composable
private fun PokemonDimensionsItemView(pokemon: PokemonDataItem) {
    CardView(modifier = Modifier.padding(top = 12.dp)) {

        Column(modifier = Modifier.padding(top = 12.dp)) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.weight, pokemon.weight),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.height, pokemon.height),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }

}

@Composable
private fun PokemonTypesItemView(type: String) {
    Text(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = type,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.background
    )
}

fun getProgressColor(progress: Float): Color {
    return when {
        progress < 0.2 -> Color.White
        progress < 0.4 -> Color.Green
        progress < 0.6 -> Color.Blue
        progress < 0.8 -> Color.Magenta
        else -> Color.Yellow
    }
}
