package dev.ozon.gitlab.plplmax.core_navigation_impl

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ozon.gitlab.plplmax.core_navigation_api.DependenciesInjector
import dev.ozon.gitlab.plplmax.core_navigation_impl.di.Dependencies
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation.ProductInDetailFragment
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import dev.ozon.gitlab.plplmax.feature_products_impl.presentation.ProductsFragment

class MainActivity : AppCompatActivity(), DependenciesInjector {

    private lateinit var interactor: ProductsInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectMainActivity(this)

        val productsRequest = OneTimeWorkRequestBuilder<ProductsWorker>().build()
        val productsInDetailRequest = OneTimeWorkRequestBuilder<ProductsInDetailWorker>().addTag("RetrofitWorker").build()

        WorkManager.getInstance(applicationContext)
            .beginUniqueWork("RetrofitWorker", ExistingWorkPolicy.KEEP, productsRequest)
            .then(productsInDetailRequest)
            .enqueue()

        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(productsInDetailRequest.id)
            .observe(this) {
                if (it != null && it.state.isFinished) {
                    Log.e("TEST", "$it")

                    val productsJson = it.outputData.getString(ProductsWorker.PRODUCTS_KEY)
                    val productsInDetailJson =
                        it.outputData.getString(ProductsInDetailWorker.PRODUCTS_IN_DETAIL_KEY)

                    val productsTypeToken = object : TypeToken<List<ProductUi>>() {}.type
                    val productsInDetailTypeToken =
                        object : TypeToken<List<ProductInDetailUi>>() {}.type

                    with(Gson()) {
                        val products = fromJson<List<ProductUi>>(productsJson, productsTypeToken)
                        val productsInDetail = fromJson<List<ProductInDetailUi>>(
                            productsInDetailJson,
                            productsInDetailTypeToken
                        )

                        Log.e("GSON", "onCreate: $products", )
                        Log.e("GSON", "onCreate: $productsInDetail", )

                        if (productsInDetail != null) {
                            Log.e("TEST", "onCreate: ${productsInDetail}", )
                            interactor.saveProducts(products)
                        }
                    }
                }
            }
    }

    override fun injectMainActivity(activity: AppCompatActivity) { // TODO: REMOVE PARAMETER
        interactor = Dependencies.featureProductsComponent.productsInteractor()
    }

    override fun injectProductsFragment(fragment: Fragment) {
        Dependencies.featureProductsComponent.inject(fragment as ProductsFragment)
    }

    override fun injectProductInDetailFragment(fragment: Fragment) {
        Dependencies.featureProductDetailComponent.inject(fragment as ProductInDetailFragment)
    }
}