package com.bity.app.ui.screen.account_information

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bity.app.ui.widget.LoadingDialog
import com.bity.icp_kotlin_kit.domain.generated_file.token
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.ICPTokenBalance
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import org.koin.androidx.compose.koinViewModel
import java.math.BigInteger

@Composable
fun AccountBalance(
    modifier: Modifier = Modifier,
    viewModel: AccountBalanceViewModel = koinViewModel()
) {
    val accountInformation = viewModel.accountInformationState

    AccountBalanceView(
        modifier = modifier,
        accountInformation = accountInformation,
        onPrincipalIdUpdated = { viewModel.onPrincipalIdUpdated(it) },
        search = { viewModel.onSearch() }
    )
}

@Composable
fun AccountBalanceView(
    modifier: Modifier = Modifier,
    accountInformation: AccountBalanceState,
    onPrincipalIdUpdated: (String) -> Unit,
    search: () -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                text = "Account Balances"
            )
        },
        content = {
            if(accountInformation.isLoading) LoadingDialog()
            Column(
                modifier = modifier.padding(it)
            ) {
                AccountPrincipalID(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    principalId = accountInformation.principalId,
                    onValueChange = { newValue ->
                        onPrincipalIdUpdated(newValue)
                    },
                    onPaste = {
                        clipboardManager.getText()?.text?.let { clipboardText ->
                            onPrincipalIdUpdated(clipboardText)
                        }
                    }
                )
                AnimatedVisibility(accountInformation.balances.isNotEmpty()) {
                    BalancesList(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        balances = accountInformation.balances
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    enabled = accountInformation.principalId.isNotEmpty(),
                    onClick = { search() }
                ) {
                    Text(
                        text = "Search"
                    )
                }
            }
        }
    )
}

@Composable
fun BalancesList(
    modifier: Modifier = Modifier,
    balances: List<ICPTokenBalance>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight(),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(balances) {
            TokenBalance(
                tokenBalance = it,
                modifier = Modifier
                    .padding(vertical = 6.dp)
            )
        }
    }
}

@Composable
fun TokenBalance(
    tokenBalance: ICPTokenBalance,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(52.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(tokenBalance.token.logoUrl)
                    .crossfade(true)
                    .build(),
                placeholder = null,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(52.dp),
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                text = tokenBalance.token.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(1F),
                text = "${tokenBalance.decimalBalance} ${tokenBalance.token.symbol}",
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }
    }
}

@Composable
fun AccountPrincipalID(
    modifier: Modifier = Modifier,
    principalId: String,
    onValueChange: (String) -> Unit,
    onPaste: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            label = {
                Text("Principal ID")
            },
            value = principalId,
            onValueChange = onValueChange,
            trailingIcon = {
                TextButton(
                    onClick = onPaste
                ) {
                    Text(
                        text = "Paste"
                    )
                }
            },
            singleLine = true,
            modifier = Modifier.weight(3F)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TokenBalancePreview(modifier: Modifier = Modifier) {
    TokenBalance(
        tokenBalance = tokenBalance
    )
}

@Preview(showBackground = true)
@Composable
fun AccountPrincipalIDPreview(
    modifier: Modifier = Modifier
) {
    AccountPrincipalID(
        principalId = "mi5lp-tjcms-b77vo-qbfgp-cjzyc-imkew-uowpv-ca7f4-l5fzx-yy6ba-qqe",
        onValueChange = { },
        onPaste = { }
    )
}

@Preview
@Composable
fun AccountBalanceViewPreview(
    modifier: Modifier = Modifier
) {
    AccountBalanceView(
        accountInformation = AccountBalanceState(
            isLoading = true,
            balances = listOf(
                tokenBalance,
                tokenBalance,
                tokenBalance
            )
        ),
        onPrincipalIdUpdated = { },
        search = { }
    )
}

private val tokenBalance = ICPTokenBalance(
    token = ICPToken(
        standard = ICPTokenStandard.ICP,
        canister = ICPSystemCanisters.TokenRegistry.icpPrincipal,
        name = "Internet Computer",
        decimals = 8,
        symbol = "ICP",
        description = "",
        totalSupply = BigInteger.ONE,
        verified = true,
        logoUrl = null,
        websiteUrl = null
    ),
    balance = BigInteger("5432434")
)