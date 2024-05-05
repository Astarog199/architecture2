package ru.gb.android.workshop2.presentation.list.product

import ru.gb.android.workshop2.domain.promo.Promo
import ru.gb.android.workshop2.domain.product.Product
import ru.gb.android.workshop2.presentation.list.product.model.ProductModel
import ru.gb.android.workshop2.presentation.common.DiscountFormatter
import ru.gb.android.workshop2.presentation.common.PriceFormatter

class ProductFactory(
    private val discountFormatter: DiscountFormatter,
    private val priceFormatter: PriceFormatter,
) {
    fun create(product: Product, promos: List<Promo>): ProductModel {
        val promoForProduct: Promo? = promos.firstOrNull { promo ->
            (promo is Promo.PromoForProducts &&
                    promo.products.any { productId -> productId == product.id })
        }
        return ProductModel(
            id = product.id,
            name = product.name,
            image = product.image,
            price = product.price.let(priceFormatter::format),
            hasDiscount = promoForProduct != null,
            discount = promoForProduct.resolveDiscount(),
        )
    }

    private fun Promo?.resolveDiscount(): String {
        return (this as? Promo.PromoForProducts)
            ?.discount
            ?.toInt()
            ?.let(discountFormatter::format)
            ?: ""
    }
}