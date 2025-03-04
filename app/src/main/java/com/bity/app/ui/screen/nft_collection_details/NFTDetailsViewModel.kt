package com.bity.app.ui.screen.nft_collection_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NFTDetailsViewModel(
    nftCanisterString: String,
    private val nftRepository: NFTRepository
) : ViewModel() {

    private val collectionPrincipal = ICPPrincipal(nftCanisterString)

    private val _state = MutableStateFlow(NFTDetailsState())
    val state: StateFlow<NFTDetailsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    getNFTDetails()
                } catch (t: Throwable) {
                    t.printStackTrace()
                    Log.e(TAG, "Error", t)
                }
            }
        }
    }

    private suspend fun getNFTDetails() = coroutineScope {
        listOf(
            async { updateNFTCollection() },
            async { updateNFTsList() }
        ).awaitAll()
        _state.value = _state.value.copy(
            isLoading = false
        )
    }

    private suspend fun updateNFTCollection() {
        val nftDetails = nftRepository.fetchCollection(collectionPrincipal)
        _state.value = _state.value.copy(
            nftCollection = nftDetails
        )
    }

    private suspend fun updateNFTsList() {
        val collectionDetails = nftRepository.fetchNFTs(collectionPrincipal)
        _state.value = _state.value.copy(
            collectionNFTs = collectionDetails
        )
    }

    companion object {
        private val TAG = "NFTDetailsViewModel"
    }

}