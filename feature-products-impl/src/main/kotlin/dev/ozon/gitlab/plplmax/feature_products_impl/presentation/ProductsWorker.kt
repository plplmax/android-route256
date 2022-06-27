package dev.ozon.gitlab.plplmax.feature_products_impl.presentation

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ozon.gitlab.plplmax.core_network_api.ProductData
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import dev.ozon.gitlab.plplmax.feature_products_impl.domain.toUi

class ProductsWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val api: ProductsApi
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val response = api.loadProducts().execute()

        return if (response.isSuccessful) {
            val products = response.body()?.map(ProductData::toUi)

            val typeToken = object : TypeToken<List<ProductUi>>() {}.type
            val json = Gson().toJson(products, typeToken)

            Result.success(workDataOf(PRODUCTS_KEY to json))
        } else Result.failure()
    }

    companion object {
        const val PRODUCTS_KEY = "PRODUCTS_KEY"
    }
}