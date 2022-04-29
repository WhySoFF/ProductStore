package com.bsuir.productlist.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.bsuir.productlist.R
import com.bsuir.productlist.databinding.LayoutProductListItemBinding
import com.bsuir.productlist.model.CurrencyRate
import com.bsuir.productlist.model.Product
import com.bsuir.productlist.util.CurrencyFormatUtil

typealias OnProductClickListener = (Product) -> Unit

class ProductAdapter(private val onProductClickListener: OnProductClickListener) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList: List<Product> = emptyList()
    private var filteredProductList: List<Product> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_list_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(filteredProductList[position])
    }

    override fun getItemCount() = filteredProductList.size

    fun setProducts(productList: List<Product>) {
        this.productList = productList
        displayList(this.productList)
    }

    private fun displayList(productList: List<Product>) {
        filteredProductList = productList
        notifyDataSetChanged()
    }

    fun filter(filter: String) {
        displayList(productList.filter {
            it.category.contains(filter, true)
                    || it.title.contains(filter, true)
                    || it.price.contains(filter, true)
        })
    }

    fun reloadList() {
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: LayoutProductListItemBinding by viewBinding()

        fun bind(product: Product) {
            with(binding) {
                title.text = product.title
                category.text = product.category
                price.text = CurrencyFormatUtil.getFormattedCurrency(product.price)
                image.load(product.image)
                root.setOnClickListener { onProductClickListener.invoke(product) }
            }
        }
    }
}