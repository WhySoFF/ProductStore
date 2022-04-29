package com.bsuir.productlist.screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContentInfo
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bsuir.productlist.R
import com.bsuir.productlist.databinding.FragmentMainScreenBinding
import com.bsuir.productlist.db.ModelDb
import com.bsuir.productlist.preferences.CurrencyPreferences
import com.bsuir.productlist.repository.CurrencyRepository
import com.bsuir.productlist.repository.DataSource
import com.bsuir.productlist.repository.StoreRepository
import com.bsuir.productlist.screen.BaseFragment
import com.bsuir.productlist.util.CurrencyFormatUtil
import com.bsuir.productlist.util.provideCurrencyApi
import com.bsuir.productlist.util.provideStoreApi
import com.bsuir.productlist.util.successValueOrNull
import com.bsuir.productlist.util.viewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainScreenFragment : BaseFragment(R.layout.fragment_main_screen) {

    private val binding: FragmentMainScreenBinding by viewBinding()
    private val viewModel: MainScreenViewModel by viewModels {
        viewModelFactory {
            val database = ModelDb.getInstance(requireContext().applicationContext)
            val sharedPrefs = requireActivity().getSharedPreferences(
                "pref",
                Context.MODE_PRIVATE
            )
            val currPreferences = CurrencyPreferences(sharedPrefs)
            MainScreenViewModel(
                StoreRepository(provideStoreApi(), database),
                CurrencyRepository(provideCurrencyApi(), currPreferences)
            )
        }
    }

    private val popupMenu by lazy {
        PopupMenu(requireContext(), binding.menuButton, Gravity.END).apply {
            menu.add(R.string.about).setOnMenuItemClickListener {
                Toast.makeText(requireContext(), R.string.about_message, Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productAdapter = ProductAdapter { clickedProduct ->
            findNavController().navigate(
                R.id.detailsFragment,
                bundleOf("product" to clickedProduct),
            )
        }
        binding.accountButton.setOnClickListener {
            findNavController().navigate(R.id.accountFragment)
        }
        binding.productList.apply { adapter = productAdapter }
        binding.menuButton.setOnClickListener { popupMenu.show() }
        binding.searchField.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            productAdapter.filter(text.toString())
        })
        binding.bynSwitch.setOnClickListener {
            if (it.isEnabled) {
                if (binding.bynSwitch.isChecked) {
                    CurrencyFormatUtil.selectedCurrency = CurrencyFormatUtil.Currency.BYN
                } else {
                    CurrencyFormatUtil.selectedCurrency = CurrencyFormatUtil.Currency.USD
                }
                productAdapter.reloadList()
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.currency_fetching_message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        lifecycleScope.launch {
            viewModel.products.collect {
                val products = it.first
                val dataSource = it.second

                binding.progressBar.isVisible = false
                if (products.isNotEmpty()) {
                    productAdapter.setProducts(products)
                }
                when (dataSource) {
                    DataSource.LOCAL -> showNoInternetToast()
                    DataSource.EMPTY -> showCantLoadMessage()
                    else -> {
                    }
                }
            }
            viewModel.usdRate.collect {
                when (it.second) {
                    DataSource.REMOTE, DataSource.LOCAL -> {
                        CurrencyFormatUtil.usdRate = it.first.toFloat()
                        binding.bynSwitch.isEnabled = true
                    }
                    else -> {
                    }
                }
            }
        }

        viewModel.showNoInternetCommand
            .onEach { showNoInternetToast() }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showNoInternetToast() {
        Toast.makeText(
            requireContext(),
            R.string.no_internet_message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showCantLoadMessage() {
        binding.cantLoadMessage.isVisible = true
    }

}