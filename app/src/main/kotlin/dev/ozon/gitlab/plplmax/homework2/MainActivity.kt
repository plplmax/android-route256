package dev.ozon.gitlab.plplmax.homework2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.ozon.gitlab.plplmax.homework2.presentation.products.productInDetail.ProductInDetailFragment

class MainActivity : AppCompatActivity(), ProductInDetailFragment.ToProductDetailNavigationListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun navigateToDetailFragment(productGuid: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ProductInDetailFragment.newInstance(productGuid))
            .addToBackStack(null)
            .commit()
    }
}