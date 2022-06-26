package dev.ozon.gitlab.plplmax.core_network_impl

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dev.ozon.gitlab.plplmax.core_network_api.ProductData
import dev.ozon.gitlab.plplmax.core_network_api.ProductInDetailData
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi
import dev.ozon.gitlab.plplmax.core_network_api.ProductsRemoteDataSource
import javax.inject.Inject

class ProductsRemoteDataSourceImpl @Inject constructor(
    private val api: ProductsApi,
    private val context: Context
) : ProductsRemoteDataSource {
    override fun getProducts(): List<ProductData> {
//        val productsRequest = OneTimeWorkRequestBuilder<ProductsWorker>()
//            .build()
//
//        WorkManager.getInstance(context)
//            .enqueue(productsRequest)

        return emptyList()
    }

    override fun getProductById(guid: String): ProductInDetailData? { // TODO: Remove nullability in the next homework
        return productsInDetailData.find { it.guid == guid }
    }
}