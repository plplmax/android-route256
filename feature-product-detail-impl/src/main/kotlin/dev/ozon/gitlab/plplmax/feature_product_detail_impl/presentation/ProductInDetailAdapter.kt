package dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import dev.ozon.gitlab.plplmax.core_utils.load
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.R

class ProductInDetailAdapter : RecyclerView.Adapter<ProductInDetailAdapter.ViewHolder>() {

    private var imageUrls: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun getItemCount(): Int = imageUrls.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitUrls(urls: List<String>) {
        imageUrls = urls
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.pagerImageView)

        fun bind(url: String) {
            imageView.load(url)
        }
    }
}