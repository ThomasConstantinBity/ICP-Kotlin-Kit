package com.bity.app.ui.screen.icp_nfts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bity.app.ui.widget.LoadingDialog
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import org.koin.androidx.compose.koinViewModel

@Composable
fun ICPNFTsScreen(
    modifier: Modifier = Modifier,
    viewModel: ICPNFTsViewModel = koinViewModel()
) {
    val state = viewModel.state
    if(state.isLoading) LoadingDialog()
    else NFTCollectionsList(
        modifier = modifier,
        nftCollections = state.nftCollections
    )
}

@Composable
private fun NFTCollectionsList(
    modifier: Modifier = Modifier,
    nftCollections: List<ICPNftCollection>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(nftCollections) {
            NFTCard(
                modifier = Modifier.padding(8.dp),
                nft = it
            )
        }
    }
}

@Composable
fun NFTCard(
    modifier: Modifier = Modifier,
    nft: ICPNftCollection
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp,
        ),
        modifier = modifier
            .height(52.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(nft.iconURL)
                    .crossfade(true)
                    .build(),
                placeholder = null,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(52.dp),
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = nft.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}