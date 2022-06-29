package dev.ozon.gitlab.plplmax.work_manager_impl

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.gson.Gson
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailMapper
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductMapper
import dev.ozon.gitlab.plplmax.work_manager_impl.ProductsInDetailWorker
import dev.ozon.gitlab.plplmax.work_manager_impl.ProductsWorker

class ProductsWorkerFactory(
    private val api: ProductsApi,
    private val productMapper: ProductMapper,
    private val productInDetailMapper: ProductInDetailMapper,
    private val gson: Gson
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            ProductsWorker::class.qualifiedName -> ProductsWorker(
                appContext,
                workerParameters,
                api,
                productMapper,
                gson
            )
            ProductsInDetailWorker::class.qualifiedName -> ProductsInDetailWorker(
                appContext,
                workerParameters,
                api,
                productInDetailMapper,
                gson
            )
            else -> null
        }
    }
}