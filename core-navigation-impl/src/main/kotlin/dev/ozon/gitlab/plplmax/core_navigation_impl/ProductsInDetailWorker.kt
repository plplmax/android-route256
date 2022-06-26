package dev.ozon.gitlab.plplmax.core_navigation_impl

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ozon.gitlab.plplmax.core_network_api.ProductInDetailData
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.domain.toUi

class ProductsInDetailWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val api: ProductsApi
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val response = api.loadProductsInDetail().execute()

        Log.e("DOWORK IN DETAIl", "doWork: $response")

        return if (response.isSuccessful) {

            val products = response.body()?.map(ProductInDetailData::toUi)

            val typeToken = object : TypeToken<List<ProductInDetailUi>>() {}.type

            val json = Gson().toJson(products, typeToken)

            Log.e("DOWORK IN DETAIl", "doWork: $json", )

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