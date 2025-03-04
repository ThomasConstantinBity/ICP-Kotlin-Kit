package com.bity.app.ui.screen.nft_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.model.nft.metadata.ICPNFTEXTMetadata
import com.bity.icp_kotlin_kit.domain.model.nft.metadata.ICPNFTMetadata
import java.math.BigInteger

@Composable
fun NFTDetailsPage (
    modifier: Modifier = Modifier,
    viewModel: NFTDetailsPageViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if(state.isLoading) LoadingDialog()

    CollectionItem(
        modifier = modifier,
        state = state
    )
}

@Composable
private fun CollectionItem(
    modifier: Modifier = Modifier,
    state: NFTDetailsPageState
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NFTImage(
            modifier = Modifier
                .padding(vertical = 12.dp),
            metadata = state.item?.metadata
        )
        NFTTitle(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp),
            state = state
        )
    }
}

@Composable
private fun NFTImage(
    modifier: Modifier = Modifier,
    metadata: ICPNFTMetadata?
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        SubcomposeAsyncImage(
            model = metadata?.thumbnailUrl,
            contentDescription = null,
            loading = { CircularProgressIndicator() }
        )
    }
}

@Composable
private fun NFTTitle(
    modifier: Modifier = Modifier,
    state: NFTDetailsPageState
) {
    val nftCollectionName = state.nftCollection?.name ?: return
    val nftId = state.item?.id ?: return
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "$nftCollectionName #${nftId + BigInteger.ONE}"
        )
    }
}