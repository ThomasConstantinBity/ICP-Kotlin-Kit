package com.bity.app.ui.screen.icp_nfts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bity.icp_kotlin_kit.domain.usecase.nft.GetAllNFTCollectionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ICPNFTsViewModel(
    private val getAllNFTCollections: GetAllNFTCollectionsUseCase
): ViewModel() {

    var state: ICPNFTsState by mutableStateOf(ICPNFTsState())
        private set

    init {
        viewModelScope.launch {
            val nftCollections = withContext(Dispatchers.IO) {
                getAllNFTCollections()
            }
            state = ICPNFTsState(
                isLoading = false,
                nftCollections = nftCollections.sortedBy { it.name }
            )
        }
    }
}