package ru.gb.android.workshop2.presentation.list.product.model

import ru.gb.android.workshop2.presentation.card.finish.ErrorProvider

data class ProductListState (
    val isLoading: Boolean = false,
    val productList: List<ProductState> = mutableListOf(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = {""}
)