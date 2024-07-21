package ru.gb.android.workshop2.presentation.list.product.model

data class ProductState (
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val price: String = "",
    val hasDiscount: Boolean = false,
    val discount: String = "",
)