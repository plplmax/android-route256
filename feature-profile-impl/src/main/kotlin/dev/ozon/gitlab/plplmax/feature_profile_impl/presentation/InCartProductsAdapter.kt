package dev.ozon.gitlab.plplmax.feature_profile_impl.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import dev.ozon.gitlab.plplmax.core_utils.BaseProductsAdapter
import dev.ozon.gitlab.plplmax.feature_profile_impl.R

class InCartProductsAdapter(
    private val deleteFromCart: (String) -> Unit,
    navigateToDetail: (String) -> Unit
) : BaseProductsAdapter(navigateToDetail) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_product_list_item, parent, false)
        return InCartViewHolder(view)
    }

    inner class InCartViewHolder(view: View) : BaseProductsAdapter.ViewHolder(view) {
        private val deleteImageView: ImageView = view.findViewById(R.id.deleteImageView)

        override fun bind(position: Int) {
            super.bind(position)

            deleteImageView.setOnClickListener {
                deleteFromCart(products[position].guid)
            }
        }
    }
}