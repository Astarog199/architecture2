package ru.gb.android.workshop2.presentation.list.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import ru.gb.android.workshop2.domain.product.ConsumeProductsUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase
import ru.gb.android.workshop2.marketsample.R
import ru.gb.android.workshop2.presentation.list.product.model.ProductListState
import ru.gb.android.workshop2.presentation.list.product.model.ProductState

class ProductListViewModel(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productFactory: ProductFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state.asStateFlow()

    fun loadProduct() {
        combine(
            consumeProductsUseCase(),
            consumePromosUseCase(),
        ) { products, promos ->
            products.map { product -> productFactory.create(product, promos) }
        }
            .onStart {
                _state.update { products -> products.copy(isLoading = true) }
            }
            .onEach { productList ->
                _state.update {
                    products -> products.copy (isLoading = false, productList = productList)
                }
            }
            .catch {
                _state.update {product ->
                    product.copy (
                        hasError = true,
                        errorProvider = {context ->
                            context.getString(R.string.error_wile_loading_data) }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        loadProduct()
    }

    fun errorShown() {
        _state.update { productScreenState -> productScreenState.copy (hasError = false) }
    }
}