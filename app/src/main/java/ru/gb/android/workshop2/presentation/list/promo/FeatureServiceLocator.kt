package ru.gb.android.workshop2.presentation.list.promo

import ru.gb.android.workshop2.ServiceLocator
import ru.gb.android.workshop2.presentation.list.promo.model.PromoStateMapper

object FeatureServiceLocator {

    fun providePromoViewModelFactory(): PromoViewModelFactory{
        return PromoViewModelFactory(
            promoStateMapper = provideProductStateMapper(),
            consumePromosUseCase =  ServiceLocator.provideConsumePromosUseCase()
        )
    }

    private fun provideProductStateMapper(): PromoStateMapper {
        return PromoStateMapper()
    }
}