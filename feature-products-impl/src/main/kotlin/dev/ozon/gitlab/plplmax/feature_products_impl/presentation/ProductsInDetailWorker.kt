package dev.ozon.gitlab.plplmax.feature_products_impl.presentation

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailMapper
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi

class ProductsInDetailWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val api: ProductsApi,
    private val mapper: ProductInDetailMapper,
    private val gson: Gson
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val response = api.loadProductsInDetail().execute()

        return if (response.isSuccessful) {
            val products = response.body()?.map(mapper::toUi)

            val typeToken = object : TypeToken<List<ProductInDetailUi>>() {}.type
            val json = gson.toJson(products, typeToken)

            Result.success(
                workDataOf(
                    PRODUCTS_IN_DETAIL_KEY to json,
                    ProductsWorker.PRODUCTS_KEY to inputData.getString(ProductsWorker.PRODUCTS_KEY)
                )
            )
        } else {
            Result.failure()
        }
    }

    companion object {
        const val PRODUCTS_IN_DETAIL_KEY = "products_in_detail_key"
    }
}