package com.example.testproject.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.common.utils.Error
import com.example.common.utils.getErrorMessageRes
import com.example.testproject.R


@Composable
fun ErrorScreen(
    error: Error,
    onTryAgainClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = error.getErrorMessageRes()),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onSurface
        )
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .requiredHeightIn(min = 44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colors.primary)
                .clickable(onClick = onTryAgainClicked),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = stringResource(id = R.string.try_again),
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h6,
            )
        }
    }
}