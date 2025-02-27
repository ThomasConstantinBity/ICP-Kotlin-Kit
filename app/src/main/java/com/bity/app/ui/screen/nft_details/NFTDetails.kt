package com.bity.app.ui.screen.nft_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bity.app.ui.widget.LoadingDialog

@Composable
fun NFTDetails(
    modifier: Modifier = Modifier,
    viewModel: NFTDetailsViewModel
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    NFTDetailsPage(
        modifier = modifier,
        state = state
    )
}

@Composable
private fun NFTDetailsPage(
    modifier: Modifier = Modifier,
    state: NFTDetailsState
) {
    if(state.isLoading) LoadingDialog()
    state.nftCollection?.let { nft ->
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(nft.iconURL)
                    .crossfade(true)
                    .build(),
                placeholder = null,
                contentDescription = "",
                contentScale = ContentScale.None,
                modifier = Modifier.size(180.dp)
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                text = nft.name,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                text = nft.description,
                textAlign = TextAlign.Center
            )
        }
    }
}