package dev.ozon.gitlab.plplmax.work_manager_impl

import android.content.Context
import androidx.work.WorkerParameters
import androidx.work.rxjava3.RxWorker
import androidx.work.workDataOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductMapper
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import io.reactivex.rxjava3.core.Single

class ProductsWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val api: ProductsApi,
    private val mapper: ProductMapper,
    private val gson: Gson
) : RxWorker(context, workerParams) {

    override fun createWork(): Single<Result> {
        return api.loadProducts()
            .map {
                val products = it.map(mapper::toUi)

                val typeToken = object : TypeToken<List<ProductUi>>() {}.type
                val json = gson.toJson(products, typeToken)

                Result.success(workDataOf(PRODUCTS_KEY to json))
            }
            .onErrorReturnItem(Result.failure())
    }

    companion object {
        const val PRODUCTS_KEY = "PRODUCTS_KEY"
    }
}