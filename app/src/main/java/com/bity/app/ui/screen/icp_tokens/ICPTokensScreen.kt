package com.bity.app.ui.screen.icp_tokens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.bity.app.ui.widget.LoadingDialog
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun ICPTokensScreen(
    modifier: Modifier = Modifier,
    viewModel: ICPTokensViewModel = koinViewModel()
) {
    val state = viewModel.state

    if(state.isLoading) {
        LoadingDialog()
    } else {
        TokensList(
            modifier = modifier,
            tokens = state.tokens
        )
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
            token.logo?.let {
                TokenImage(
                    logo = it,
                    modifier = Modifier.size(52.dp)
                )
            }
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = token.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun TokenImage(
    logo: String,
    modifier: Modifier = Modifier
) {

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(logo) {
        when {
            logo.startsWith("data:image") ->
                imageBitmap = base64StringToImageBitmap(logo)
        }
    }

    if(imageBitmap != null) {
        Image(
            bitmap = imageBitmap!!,
            contentDescription = null,
            modifier = modifier
        )
    } else {
        SubcomposeAsyncImage(
            modifier = modifier,
            model = logo,
            contentDescription = null,
            loading = { CircularProgressIndicator() }
        )
    }
}

suspend fun base64StringToImageBitmap(base64String: String): ImageBitmap? =
    withContext(Dispatchers.IO) {
        val pureBase64Encoded = base64String.substringAfter(",")
        return@withContext try {
            val decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            bitmap.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
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
            spam = true,
            logo = null,
        )
    )
}