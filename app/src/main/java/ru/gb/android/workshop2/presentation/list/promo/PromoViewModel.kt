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
import ru.gb.android.workshop2.marketsample.R
import ru.gb.android.workshop2.presentation.list.promo.model.PromoState
import ru.gb.android.workshop2.presentation.list.promo.model.PromoStateMapper
import ru.gb.android.workshop2.presentation.list.promo.model.PromosListState

class PromoViewModel(
    private val promoStateMapper: PromoStateMapper,
    private val consumePromosUseCase: ConsumePromosUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(PromosListState())
    val state: StateFlow<PromosListState> = _state.asStateFlow()
    val arr = mutableListOf<PromoState>()

    fun loadPromos() {
        consumePromosUseCase()
            .map { promos ->
                promos.map(promoStateMapper::map)
            }
            .onStart {
                _state.update { promoList -> promoList.copy(isLoading = true) }
            }
            .onEach {promoState ->
                fillList(promoState)
                _state.update {
                    promoList -> promoList.copy( promosList = arr)
            }
            }
            .catch {
                _state.update {promoList ->
                    promoList.copy (hasError = true,
                        errorProvider = {context ->
                            context.getString(R.string.error_wile_loading_data) }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun clearList(){
        arr.clear()
    }

    fun fillList(promoState: List<PromoState>) {
        for (i in  promoState) {
            arr.add(i)
        }
    }

    fun refresh() {
        loadPromos()
    }
}