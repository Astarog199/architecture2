package ru.gb.android.workshop2.presentation.list.promo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase
import ru.gb.android.workshop2.presentation.list.promo.model.PromoState
import ru.gb.android.workshop2.presentation.list.promo.model.PromoStateMapper

class PromoViewModel(
    private val promoStateMapper: PromoStateMapper,
    private val consumePromosUseCase: ConsumePromosUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(PromoState())
    val state: StateFlow<PromoState> = _state.asStateFlow()

    fun loadPromos() {
        consumePromosUseCase()
            .map { promos ->
                promos.map(promoStateMapper::map)
            }
            .onStart {

            }
            .onEach {promoState ->
                _state.update {
                    promoState -> promoState.copy()
            }
            }
            .catch {

            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        loadPromos()
    }

}