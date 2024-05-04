package ru.gb.android.workshop2.presentation.list.promo.model

import ru.gb.android.workshop2.presentation.card.finish.ErrorProvider


data class PromoLoadState (
    val isLoading: Boolean = false,
    val productState: PromoState = PromoState(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = {""}
)