package com.bity.app.ui.screen.icp_tokens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bity.app.ui.widget.LoadingDialog
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import org.koin.androidx.compose.koinViewModel
import java.math.BigInteger

@Composable
fun ICPTokensScreen(
    modifier: Modifier = Modifier,
    viewModel: ICPTokensViewModel = koinViewModel()
) {
    val state = viewModel.state

    if(state.isLoading) {
        LoadingDialog()
    } else {
        TokensList(tokens = state.tokens)
    }
}

@Composable
private fun TokensList(
    modifier: Modifier = Modifier,
    tokens: List<ICPToken>
) {
    LazyColumn(modifier = modifier) {
        items(tokens) {
            TokenCard(
                modifier = Modifier.padding(8.dp),
                token = it
            )
        }
    }
}

@Composable
private fun TokenCard(
    modifier: Modifier = Modifier,
    token: ICPToken
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
                    .data(token.logoUrl)
                    .crossfade(true)
                    .build(),
                placeholder = null,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(52.dp),
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = token.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TokenCardPreview() {
    TokenCard(
        modifier = Modifier.padding(8.dp),
        token = ICPToken(
            standard = ICPTokenStandard.ICRC1,
            canister = ICPSystemCanisters.Ledger.icpPrincipal,
            name = "New ICP Token",
            decimals = 8,
            symbol = "ICPTK",
            description = "A demo ICP Token",
            totalSupply = BigInteger.ONE,
            verified = true,
            logoUrl = null,
            websiteUrl = null
        )
    )
}