package com.bity.app.ui.screen.icp_nfts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bity.icp_kotlin_kit.domain.service.NFTService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ICPNFTsViewModel(
    private val nftService: NFTService
): ViewModel() {

    var state: ICPNFTsState by mutableStateOf(ICPNFTsState())
        private set

    init {
        viewModelScope.launch {
            val nftCollections = withContext(Dispatchers.IO) {
                nftService.fetchAllCollections()
            }
            state = ICPNFTsState(
                isLoading = false,
                nftCollections = nftCollections.sortedBy { it.name }
            )
        }
    }
}