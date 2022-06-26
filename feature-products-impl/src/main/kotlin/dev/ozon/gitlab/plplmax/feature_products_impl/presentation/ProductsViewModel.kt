package dev.ozon.gitlab.plplmax.feature_products_impl.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import java.util.*

class ProductsViewModel(
    context: Context,
    viewLifecycleOwner: LifecycleOwner,
    private val interactor: ProductsInteractor
) : ViewModel() {

    private val _productLD = MutableLiveData<List<ProductUi>>()
    val productLD: LiveData<List<ProductUi>> = _productLD

    init {
        WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkLiveData("RetrofitWorker")
            .observe(viewLifecycleOwner) {
                if (it.isEmpty()) return@observe

                if (it.first().state.isFinished) {
                    _productLD.value = interactor.getProducts()
                }
            }
    }

    fun saveProducts() = interactor.saveProducts(productLD.value!!)
}