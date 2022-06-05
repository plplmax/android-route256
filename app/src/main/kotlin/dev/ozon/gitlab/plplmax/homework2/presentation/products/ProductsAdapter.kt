package dev.ozon.gitlab.plplmax.homework2.presentation.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ozon.gitlab.plplmax.homework2.R

class ProductsAdapter(
    private val block: (String) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private var products: List<ProductUi> = emptyList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productIV: ImageView = view.findViewById(R.id.productIV)
        val nameTV: TextView = view.findViewById(R.id.nameTV)
        val priceTV: TextView = view.findViewById(R.id.priceTV)
        val ratingView: RatingBar = view.findViewById(R.id.ratingView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        with(holder) {
            Glide.with(productIV)
                .load(product.image)
                .into(productIV)

            nameTV.text = product.name
            priceTV.text = product.price
            ratingView.rating = product.rating.toFloat()

            (nameTV.parent as ConstraintLayout).setOnClickListener { block(product.guid) }
        }
    }

    override fun getItemCount(): Int = products.size

    fun submitList(list: List<ProductUi>) {
        products = list
        notifyDataSetChanged()
    }
}