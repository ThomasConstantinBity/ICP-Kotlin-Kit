package com.bity.app.ui.screen.account_nft_holding

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.bity.app.ui.widget.LoadingDialog
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountNFTHolding(
    viewModel: AccountNFTHoldingViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var principal by rememberSaveable {
        mutableStateOf("cae3j-vi4cf-pyafe-s6wrq-k6f73-2auvo-hr4ir-kovqi-pboap-nu47v-4qe")
    }

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(
                isVisible = principal != "",
                onClick = {
                    viewModel.fetchNFTHoldings(principal)
                }
            )
        }
    ) {
        when (val currentState = state) {
            is AccountNFTHoldingState.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    "Error: ${currentState.errorMessage}",
                    Toast.LENGTH_LONG
                ).show()
                NFTBalanceView(
                    modifier = Modifier
                        .padding(it)
                        .padding(top = 16.dp),
                    holdings = emptyList(),
                    principal = principal,
                    onValueChange = { value -> principal = value }
                )
            }
            AccountNFTHoldingState.Loading -> LoadingDialog()
            is AccountNFTHoldingState.Result -> NFTBalanceView(
                modifier = Modifier
                    .padding(it)
                    .padding(top = 16.dp),
                holdings = currentState.nftHoldings,
                principal = principal,
                onValueChange = { value -> principal = value }
            )
        }

    }
}

@Composable
fun NFTBalanceView(
    modifier: Modifier,
    holdings: List<ICPNFTDetails>,
    principal: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Principal(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            uncompressedPubKey = principal
        ) { onValueChange(it) }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(holdings) {
                NFTHoldingCard(
                    modifier = Modifier.
                        padding(horizontal = 4.dp, vertical = 8.dp),
                    nft = it,
                )
            }
        }
    }
}

@Composable
fun NFTHoldingCard(
    modifier: Modifier = Modifier,
    nft: ICPNFTDetails
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            SubcomposeAsyncImage(
                model = nft.metadata?.thumbnailUrl,
                contentDescription = null,
                loading = {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                text = "${nft.name}"
            )
        }
    }
}

@Composable
fun Principal(
    modifier: Modifier = Modifier,
    uncompressedPubKey: String,
    onValueChange: (String) -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        OutlinedTextField(
            label = {
                Text("Principal")
            },
            value = uncompressedPubKey,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.weight(3F)
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedButton(
            modifier = Modifier
                .weight(1F),
            onClick = {
                clipboardManager.getText()?.text?.let {
                    onValueChange(it)
                }
            })
        {
            Text(text = "Paste")
        }
    }
}

@Composable
private fun TopBar() {
    Text(
        text = "NFT Holdings",
        style = MaterialTheme.typography.bodyLarge,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
private fun BottomBar(
    isVisible: Boolean,
    onClick: () -> Unit
) {
    if(isVisible) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Button(
                onClick = { onClick() }
            ) {
                Text(
                    text = "Get Balance"
                )
            }
        }
    }
}