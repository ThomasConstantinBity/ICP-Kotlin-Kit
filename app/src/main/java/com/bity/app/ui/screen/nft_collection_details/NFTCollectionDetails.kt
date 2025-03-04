package com.bity.app.ui.screen.nft_collection_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.bity.app.ui.widget.LoadingDialog
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import java.math.BigInteger

@Composable
fun NFTCollectionDetails(
    modifier: Modifier = Modifier,
    viewModel: NFTDetailsViewModel,
    onNftClick: (BigInteger) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    NFTCollectionDetailsPage(
        modifier = modifier,
        state = state,
        onNftClick = onNftClick
    )
}

@Composable
private fun NFTCollectionDetailsPage(
    modifier: Modifier = Modifier,
    state: NFTDetailsState,
    onNftClick: (BigInteger) -> Unit
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
                nftItem = it,
                onClick = onNftClick
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
    nftItem: ICPNFTCollectionItem,
    onClick: (BigInteger) -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 8.dp),
        onClick = { onClick(nftItem.id) }
    ) {
        Column {
            SubcomposeAsyncImage(
                model = nftItem.metadata?.thumbnailUrl,
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