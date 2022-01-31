package com.example.codingapp.presentation.scene

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.codingapp.domain.usecase.*
import com.example.codingapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    observeBtcPriceUseCase: ObserveBtcPriceUseCase,
    observeMinRateUseCase: ObserveMinRateUseCase,
    observeMaxRateUseCase: ObserveMaxRateUseCase,
    private val setMinRangeUseCase: SetMinRangeUseCase,
    private val setMaxRangeUseCase: SetMaxRangeUseCase,
) : BaseViewModel<MainState, MainEffect>() {

    private val _minRate = MutableLiveData<Float>()
    val minRate: LiveData<Float>
        get() = _minRate

    private val _maxRate = MutableLiveData<Float>()
    val maxRate: LiveData<Float>
        get() = _maxRate

    init {
        observeBtcPriceUseCase.execute(Unit)
            .onEach { postState(MainState.ShowBtcPrice(it)) }
            .launchNoLoading()

        observeMinRateUseCase.execute(Unit)
            .onEach { _minRate.postValue(it) }
            .launchNoLoading()

        observeMaxRateUseCase.execute(Unit)
            .onEach { _maxRate.postValue(it) }
            .launchNoLoading()
    }


    fun setMinRange(price: Float) {
        setMinRangeUseCase
            .launch(SetMinRangeUseCase.Params(price)) {
                onSuccess = { postEffect(MainEffect.UpdatedRates) }
            }
    }

    fun setMaxRange(price: Float) {
        setMaxRangeUseCase.launch(SetMaxRangeUseCase.Params(price)) {
            onSuccess = { postEffect(MainEffect.UpdatedRates) }
        }
    }

}

sealed class MainState {
    data class ShowBtcPrice(val price: Float) : MainState()
}

sealed class MainEffect {
    object UpdatedRates : MainEffect()
}