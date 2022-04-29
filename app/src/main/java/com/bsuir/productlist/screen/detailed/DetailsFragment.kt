package com.bsuir.productlist.screen.detailed

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.bsuir.productlist.R
import com.bsuir.productlist.databinding.FragmentDetailsBinding
import com.bsuir.productlist.model.Product
import com.bsuir.productlist.screen.BaseFragment
import com.bsuir.productlist.util.CurrencyFormatUtil

class DetailsFragment: BaseFragment(R.layout.fragment_details) {

    private val binding: FragmentDetailsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener { findNavController().popBackStack() }

        val product = arguments?.getParcelable<Product>("product")
        product?.let {
            with(binding) {
                image.load(product.image)
                category.text = product.category
                title.text = product.title
                price.text = CurrencyFormatUtil.getFormattedCurrency(product.price)
                description.text = product.description
            }
        }

    }

}