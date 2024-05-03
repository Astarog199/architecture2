package ru.gb.android.workshop2.presentation.card.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.gb.android.workshop2.domain.product.ConsumeFirstProductUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase
import ru.gb.android.workshop2.marketsample.R
import ru.gb.android.workshop2.presentation.card.start.states.ProductScreenState

class ProductsViewModel(
    private val consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
    ): ViewModel(), ViewModelProvider.Factory {
    private val _state = MutableStateFlow(ProductScreenState())
    val state: StateFlow<ProductScreenState> =_state.asStateFlow()

    fun loadProduct() {
        combine(
            consumeFirstProductUseCase(),
            consumePromosUseCase(),
        ) { product, promos -> productStateFactory.create(product, promos) }
//            .flowOn(Dispatchers.IO)
            .onStart {
                _state.update { productScreenState -> productScreenState.copy(isLoading = true) }
            }
            .onEach {productState ->
                _state.update { productScreenState -> productScreenState.copy(
                        isLoading = false,
                        productState = productState,
                    )
                }
            }
            .catch {
                cheduleRefresh()

                _state.update {productScreenState ->
                    productScreenState.copy (hasError = true,
                        errorProvider = {context ->
                            context.getString(R.string.error_wile_loading_data) }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun clearError(){
        _state.update { productScreenState -> productScreenState.copy(hasError = false)  }
    }

    private suspend fun cheduleRefresh() {
        viewModelScope.launch {
        delay(5000)
        loadProduct()
        }
    }
}