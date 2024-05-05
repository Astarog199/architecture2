package ru.gb.android.workshop2.presentation.list.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.workshop2.domain.product.ConsumeProductsUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase


class ProductListViewModelFactory(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productFactory: ProductFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(ProductListViewModel::class.java) ->{
                @Suppress("UNCHEKED_CAST")
                return ProductListViewModel(
                    consumeProductsUseCase = consumeProductsUseCase,
                    productFactory= productFactory,
                    consumePromosUseCase = consumePromosUseCase
                ) as  T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}