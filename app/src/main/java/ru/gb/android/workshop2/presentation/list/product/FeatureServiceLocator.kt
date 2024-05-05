package ru.gb.android.workshop2.presentation.list.product

import ru.gb.android.workshop2.ServiceLocator

object FeatureServiceLocator {

    fun provideProductViewModelFactory(): ProductListViewModelFactory {
        return ProductListViewModelFactory(
            consumeProductsUseCase = ServiceLocator.provideConsumeProductsUseCase(),
            productFactory = provideProductFactory(),
            consumePromosUseCase = ServiceLocator.provideConsumePromosUseCase(),
        )
    }

    private fun provideProductFactory(): ProductFactory {
        return ProductFactory(
            discountFormatter = ServiceLocator.provideDiscountFormatter(),
            priceFormatter = ServiceLocator.providePriceFormatter(),
        )
    }
}