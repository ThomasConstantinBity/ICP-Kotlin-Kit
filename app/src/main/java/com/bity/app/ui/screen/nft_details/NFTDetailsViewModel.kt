package com.bity.app.ui.screen.nft_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.usecase.nft.GetNFTCollection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NFTDetailsViewModel(
    nftCanisterString: String,
    private val getNFTCollection: GetNFTCollection
) : ViewModel() {

    private val canisterPrincipal = ICPPrincipal(nftCanisterString)

    private val _state = MutableStateFlow(NFTDetailsState())
    val state: StateFlow<NFTDetailsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val nftDetails = getNFTCollection(canisterPrincipal)
            _state.value = _state.value.copy(
                nftCollection = nftDetails
            )
        }
    }


}