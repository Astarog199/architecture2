package ru.gb.android.workshop2.presentation.list.promo.model

import ru.gb.android.workshop2.presentation.card.finish.ErrorProvider


data class PromosListState (
    val isLoading: Boolean = false,
    val promosList: List<PromoState> = mutableListOf(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = {""}
)