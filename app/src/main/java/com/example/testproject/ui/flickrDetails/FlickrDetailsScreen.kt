package com.example.testproject.ui.flickrDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.repository.data.FlickrDataItem
import com.example.testproject.R
import com.example.testproject.ui.views.ErrorScreen
import com.example.testproject.ui.views.LoadingScreen
import timber.log.Timber

@Composable
fun FlickrDetailsScreen(
    navController: NavHostController,
    viewModel: FlickrDetailsViewModel = hiltViewModel(),
) {
    val viewState by viewModel.state.collectAsState()
    Timber.e(viewState.toString())
    when {
        viewState.loading -> LoadingScreen()
        viewState.error != null -> ErrorScreen(
            error = viewState.error!!,
            onTryAgainClicked = { viewModel.onTryAgainClicked() })
        viewState.url != null -> {
            FlickrDetails(viewState.url!!)
        }
    }
}

@Composable
private fun FlickrDetails(url: String) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        FlickrCardView(url)
        FlickrFakeData()
    }
}

@Composable
private fun FlickrCardView(url: String) {
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
            FlickrName()
            Image(url)
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
private fun FlickrName() {
    Text(
        text = "Fake title",
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.onSurface
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Image(flickr: String) {
    val roundedCornersSize = 24.dp

    GlideImage(
        modifier = Modifier
            .height(250.dp),
        model = flickr,
        contentDescription = null,
    ) {
        it
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(RoundedCorners(roundedCornersSize.value.toInt()))
            .centerCrop()

    }
}


@Composable
private fun FlickrFakeData() {
    CardView(modifier = Modifier.padding(top = 12.dp)) {

        Column(modifier = Modifier.padding(top = 12.dp)) {
            Text(
                modifier = Modifier,
                text = "This is fake details data and not fetched?",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Wait is this also fake details data and not fetched??",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
            )
        }
    }

}
