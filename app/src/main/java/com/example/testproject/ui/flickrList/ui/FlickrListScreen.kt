package com.example.testproject.ui.flickrList.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
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
import com.example.testproject.ui.flickrList.FlickrListViewModel
import com.example.testproject.ui.flickrList.FlickrViewState
import com.example.testproject.ui.views.ErrorScreen
import com.example.testproject.ui.views.LoadingScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FlickrListScreen(
    navController: NavHostController,
    viewModel: FlickrListViewModel = hiltViewModel(),

    ) {
    val viewState by viewModel.viewStateFlow.collectAsState()


    var flickrDataItem: FlickrDataItem? by remember { mutableStateOf(null) }
    if (flickrDataItem != null) {
        val encodedUrl = URLEncoder.encode(flickrDataItem!!.urlMedium, StandardCharsets.UTF_8.toString())
        navController.navigate("details/${encodedUrl}")
        flickrDataItem = null
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchInputField(searchText = viewState.searchText,onSearchTextUpdated = viewModel::onSearchTextUpdated)
        when {
            viewState.isLoading -> LoadingScreen()
            viewState.error != null -> ErrorScreen(
                error = viewState.error!!,
                onTryAgainClicked = { viewModel.onTryAgainClicked() })
            else -> {
                FlickrList(
                    list = viewState.flickrData?.photoList ?: emptyList(),
                    isLoadingPaging = viewState.isLoadingPaging,
                    onFlickrClicked = { flickrDataItem = it },
                    onScrolledToBottom = { viewModel.onScrolledToBottom() })

            }
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchInputField(
    searchText: String,
    onSearchTextUpdated: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
        ) {

            BasicTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .focusRequester(FocusRequester.Default)
                    .onFocusChanged {
                        if (it.isFocused) {
                            keyboardController?.show()
                        } else {
                            keyboardController?.hide()
                        }
                    },
                value = searchText,
                singleLine = true,
                textStyle = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onSurface),
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                onValueChange = {
                    onSearchTextUpdated(it)
                },
            )
            if (searchText.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.search_text_hint),
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun FlickrList(
    list: List<FlickrDataItem>,
    isLoadingPaging: Boolean,
    onFlickrClicked: (FlickrDataItem) -> Unit,
    onScrolledToBottom: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(list) { index, flickr ->
                if (list.size - 1 == index) {
                    onScrolledToBottom()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(top = 8.dp)
                        .padding(horizontal = 8.dp)
                        .shadow(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = MaterialTheme.colors.onBackground)
                        .clickable {
                            onFlickrClicked(flickr)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    FlickrImage(url = flickr.urlSmall)
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = flickr.title,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
        AnimatedVisibility(visible = isLoadingPaging) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onBackground)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}


@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun FlickrImage(url: String) {
    val roundedCornersSize = 8.dp
    GlideImage(
        modifier = Modifier
            .padding(end = 16.dp)
            .width(100.dp),
        model = url,
        contentDescription = null,
    ) {
        it
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(RoundedCorners(roundedCornersSize.value.toInt()))
            .centerCrop()

    }
}
