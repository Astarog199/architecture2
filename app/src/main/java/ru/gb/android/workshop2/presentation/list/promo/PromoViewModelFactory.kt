package ru.gb.android.workshop2.presentation.list.promo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase
import ru.gb.android.workshop2.presentation.list.promo.model.PromoStateMapper

class PromoViewModelFactory(
    private val promoStateMapper: PromoStateMapper,
    private val consumePromosUseCase: ConsumePromosUseCase,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(PromoViewModel::class.java) -> {
                @Suppress("UNCHEKED_CAST")
                return PromoViewModel(
                    promoStateMapper = promoStateMapper,
                    consumePromosUseCase = consumePromosUseCase
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}