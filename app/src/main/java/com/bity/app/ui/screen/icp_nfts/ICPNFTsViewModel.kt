package com.bity.app.ui.screen.icp_nfts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchAllNFTCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ICPNFTsViewModel(
    private val fetchAllNFTCollections: FetchAllNFTCollections
): ViewModel() {

    var state: ICPNFTsState by mutableStateOf(ICPNFTsState())
        private set

    init {
        viewModelScope.launch {
            val nftCollections = withContext(Dispatchers.IO) {
                fetchAllNFTCollections()
            }
            state = ICPNFTsState(
                isLoading = false,
                nftCollections = nftCollections.sortedBy { it.name }
            )
        }
    }
}