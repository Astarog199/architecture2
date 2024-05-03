package ru.gb.android.workshop2.presentation.card.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.workshop2.domain.product.ConsumeFirstProductUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase

class ProductsViewModelFactory (
    private val consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    private val productStateFactory : ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProductsViewModel::class.java) ->{
                @Suppress("UNCHEKED_CAST")
                return ProductsViewModel(
                    consumeFirstProductUseCase = consumeFirstProductUseCase,
                    productStateFactory = productStateFactory,
                    consumePromosUseCase = consumePromosUseCase
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}