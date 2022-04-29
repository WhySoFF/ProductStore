package com.bsuir.productlist.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.productlist.repository.ICurrencyRepository
import com.bsuir.productlist.repository.IStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val storeRepository: IStoreRepository,
    private val currencyRepository: ICurrencyRepository
) : ViewModel() {

    val products = flow { emit(storeRepository.getProductList()) }

    val usdRate = flow { emit(currencyRepository.getUsdRate()) }

    private val _showNoInternetCommand = MutableSharedFlow<Unit>()
    val showNoInternetCommand: Flow<Unit> = _showNoInternetCommand

}