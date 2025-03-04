package com.bity.app.ui.screen.nft_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class NFTDetailsPageViewModel(
    collectionCanister: String,
    nftId: String,
    private val nftRepository: NFTRepository
) : ViewModel() {

    private val nftId = BigInteger(nftId)
    private val collectionPrincipal = ICPPrincipal(collectionCanister)

    private val _state = MutableStateFlow<NFTDetailsPageState>(NFTDetailsPageState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                listOf(
                    async { updateNFTMetadata() },
                    async { updateCollectionInformation() }
                ).awaitAll()
            }
        }
    }

    private suspend fun updateNFTMetadata() {
        val collectionItem = nftRepository.fetchCollectionNFT(
            collectionPrincipal = collectionPrincipal,
            nftId = nftId
        )
        _state.value = _state.value.copy(
            item = collectionItem
        )
    }

    private suspend fun updateCollectionInformation() {
        val collection = nftRepository.fetchNFTCollection(collectionPrincipal)
        _state.value = _state.value.copy(
            nftCollection = collection
        )

    }

}