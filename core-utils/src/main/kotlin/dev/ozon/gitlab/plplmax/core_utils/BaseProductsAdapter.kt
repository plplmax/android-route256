package dev.ozon.gitlab.plplmax.core_utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import dev.ozon.gitlab.plplmax.core_resources.R
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi

open class BaseProductsAdapter(
    private val block: (String) -> Unit
) : RecyclerView.Adapter<BaseProductsAdapter.ViewHolder>() {

    protected var products: List<ProductUi> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_product_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = products.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ProductUi>) {
        products = list
        notifyDataSetChanged()
    }

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val productViewContainer: MaterialCardView =
            view.findViewById(R.id.productViewContainer)
        private val productIV: ImageView = view.findViewById(R.id.productIV)
        private val nameTV: TextView = view.findViewById(R.id.nameTV)
        private val priceTV: TextView = view.findViewById(R.id.priceTV)
        private val ratingView: RatingBar = view.findViewById(R.id.ratingView)
        private val counterLabel: TextView = view.findViewById(R.id.counterLabel)
        private val counterView: TextView = view.findViewById(R.id.counterView)

        open fun bind(position: Int) {
            val product = products[position]

            productIV.load(product.image[0])

            nameTV.text = product.name
            priceTV.text = product.price
            ratingView.rating = product.rating.toFloat()

            if (product.countViews == 0) {
                hideCounter()
            } else {
                showCounter()
                counterView.text = product.countViews.toString()
            }

            productViewContainer.setOnClickListener {
                ++product.countViews
                block(product.guid)
            }
        }

        private fun showCounter() = changeCounterVisibility(hideCounter = false)

        private fun hideCounter() = changeCounterVisibility(hideCounter = true)

        private fun changeCounterVisibility(hideCounter: Boolean) {
            counterLabel.visibility = if (hideCounter) View.GONE else View.VISIBLE
            counterView.visibility = if (hideCounter) View.GONE else View.VISIBLE
        }
    }
}