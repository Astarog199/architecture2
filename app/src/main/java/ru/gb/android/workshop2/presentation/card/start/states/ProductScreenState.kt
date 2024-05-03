package ru.gb.android.workshop2.presentation.card.start.states

import ru.gb.android.workshop2.presentation.card.finish.ErrorProvider

data class ProductScreenState (
    val isLoading: Boolean = false,
    val productState: ProductState = ProductState(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = {""}
    )