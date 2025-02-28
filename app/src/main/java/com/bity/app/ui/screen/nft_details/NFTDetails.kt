package com.bity.app.ui.screen.nft_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.bity.app.ui.widget.LoadingDialog
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import java.math.BigInteger

@Composable
fun NFTDetails(
    modifier: Modifier = Modifier,
    viewModel: NFTDetailsViewModel,
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
    state: NFTDetailsState,
) {
    if(state.isLoading) LoadingDialog()
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 100.dp)
    ) {
        item(span = { GridItemSpan(this.maxLineSpan) }) {
           Header(
               nftCollection = state.nftCollection
           )
        }
        items(state.collectionNFTs) {
            NFTCard(
                nftItem = it
            )
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    nftCollection: ICPNftCollection?,
) {
    nftCollection?.let { nft ->
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = nftCollection.iconURL,
                contentDescription = null,
                loading = { CircularProgressIndicator() }
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

@Composable
fun NFTCard(
    modifier: Modifier = Modifier,
    nftItem: ICPNFTCollectionItem
) {
    Card(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Column {
            SubcomposeAsyncImage(
                model = nftItem.thumbnail,
                contentDescription = null,
                loading = { CircularProgressIndicator() }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                text = "${nftItem.id}"
            )
        }
    }
}