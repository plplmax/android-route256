package dev.ozon.gitlab.plplmax.homework2.di

import dev.ozon.gitlab.plplmax.homework2.data.products.repository.MockProductsRepositoryImpl
import dev.ozon.gitlab.plplmax.homework2.domain.products.interactor.ProductsInteractor

// Подробнее можете почитать [тут](http://sergeyteplyakov.blogspot.com/2013/03/di-service-locator.html),
 // [тут](https://habr.com/ru/post/465395/) и [тут](https://javatutor.net/articles/j2ee-pattern-service-locator).

object ServiceLocator {
    val productsInteractor: ProductsInteractor by lazy {
        ProductsInteractor.Base(
            MockProductsRepositoryImpl()
        )
    }
}
