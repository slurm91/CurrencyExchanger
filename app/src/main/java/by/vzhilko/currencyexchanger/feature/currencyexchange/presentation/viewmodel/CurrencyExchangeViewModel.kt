package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.vzhilko.currencyexchanger.core.util.mapState
import by.vzhilko.currencyexchanger.core.util.toBigDecimal
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.BalanceData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyExchangeData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.interactor.CurrencyExchangeInteractor
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.state.CurrencyExchangeState
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.util.TwoSignsDecimalFormatter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.math.BigDecimal
import kotlin.coroutines.coroutineContext

class CurrencyExchangeViewModel(
    private val interactor: CurrencyExchangeInteractor
) : ViewModel() {

    private val _initializationStateFlow: MutableStateFlow<CurrencyExchangeState<Unit>> = MutableStateFlow(CurrencyExchangeState.NoState)
    val initializationStateFlow: StateFlow<CurrencyExchangeState<Unit>> = _initializationStateFlow.asStateFlow()

    private val _balancesDataListStateFlow: MutableStateFlow<CurrencyExchangeState<List<BalanceData>>> = MutableStateFlow(CurrencyExchangeState.NoState)
    val balancesDataListStateFlow: StateFlow<CurrencyExchangeState<List<BalanceData>>> = _balancesDataListStateFlow.asStateFlow()

    private val _sellCurrenciesListStateFlow: MutableStateFlow<CurrencyExchangeState<List<CurrencyData>>> = MutableStateFlow(CurrencyExchangeState.NoState)
    val sellCurrenciesListStateFlow: StateFlow<CurrencyExchangeState<List<CurrencyData>>> = _sellCurrenciesListStateFlow.asStateFlow()

    private val _receiveCurrenciesListStateFlow: MutableStateFlow<CurrencyExchangeState<List<CurrencyData>>> = MutableStateFlow(CurrencyExchangeState.NoState)
    val receiveCurrenciesListStateFlow: StateFlow<CurrencyExchangeState<List<CurrencyData>>> = _receiveCurrenciesListStateFlow.asStateFlow()

    private val _receiveCurrencyBalanceStateFlow: MutableStateFlow<CurrencyExchangeState<BigDecimal>> = MutableStateFlow(CurrencyExchangeState.NoState)
    val receiveCurrencyBalanceStateFlow: StateFlow<CurrencyExchangeState<String>> = _receiveCurrencyBalanceStateFlow
            .asStateFlow()
            .mapState(
                scope = viewModelScope,
                initialValue = CurrencyExchangeState.NoState
            ) { state ->
                convertReceiveCurrencyBalanceState(state)
            }
    private val decimalFormatter: TwoSignsDecimalFormatter = TwoSignsDecimalFormatter()

    private val _refreshBalanceStateFlow: MutableSharedFlow<CurrencyExchangeState<String>> = MutableSharedFlow(replay = 0)
    val refreshBalanceStateFlow: SharedFlow<CurrencyExchangeState<String>> = _refreshBalanceStateFlow.asSharedFlow()

    private var currencyExchangeData: CurrencyExchangeData? = null

    private val _sellCurrencyBalanceStateFlow: MutableStateFlow<CurrencyExchangeState<String>> = MutableStateFlow(CurrencyExchangeState.NoState)
    val sellCurrencyBalanceStateFlow: StateFlow<CurrencyExchangeState<String>> = _sellCurrencyBalanceStateFlow.asStateFlow()

    private val _sellCurrencyStateFlow: MutableStateFlow<CurrencyExchangeState<CurrencyData>> = MutableStateFlow(CurrencyExchangeState.NoState)
    val sellCurrencyStateFlow: StateFlow<CurrencyExchangeState<CurrencyData>> = _sellCurrencyStateFlow.asStateFlow()

    private val _receiveCurrencyStateFlow: MutableStateFlow<CurrencyExchangeState<CurrencyData>> = MutableStateFlow(CurrencyExchangeState.NoState)
    val receiveCurrencyStateFlow: StateFlow<CurrencyExchangeState<CurrencyData>> = _receiveCurrencyStateFlow.asStateFlow()

    init {
        getRatesAtFirstTime()
    }

    private fun getRatesAtFirstTime() {
        _initializationStateFlow.value = CurrencyExchangeState.Loading
        viewModelScope.launch {
            val currencyExchangeDataState: CurrencyExchangeState<CurrencyExchangeData> = interactor.getRates()
            when {
                currencyExchangeDataState is CurrencyExchangeState.Success -> {
                    currencyExchangeData = currencyExchangeDataState.value
                    _balancesDataListStateFlow.value = interactor.getBalancesList(currencyExchangeData)
                    _sellCurrenciesListStateFlow.value = interactor.getSellCurrenciesList(currencyExchangeData!!)
                    _receiveCurrenciesListStateFlow.value = interactor.getReceiveCurrenciesList(currencyExchangeData!!)
                    _initializationStateFlow.value = CurrencyExchangeState.Success(Unit)
                }
                currencyExchangeDataState is CurrencyExchangeState.Error -> {
                    _initializationStateFlow.value = CurrencyExchangeState.Error(currencyExchangeDataState.error)
                }
                else -> {}
            }
        }
    }

    suspend fun startRefreshRates() {
        val refreshDelayTime = 5000L
        while (coroutineContext.isActive) {
            delay(refreshDelayTime)
            val currencyExchangeDataState: CurrencyExchangeState<CurrencyExchangeData> = interactor.getRates()
            if (currencyExchangeDataState is CurrencyExchangeState.Success) {
                currencyExchangeData = currencyExchangeDataState.value
                exchangeCurrency()
            }
        }
    }

    fun onSellCurrencyBalanceChanged(balance: String?) {
        _sellCurrencyBalanceStateFlow.value = if (balance != null) {
            CurrencyExchangeState.Success(balance)
        } else {
            CurrencyExchangeState.NoState
        }

        exchangeCurrency()
    }

    fun onSellCurrencySelected(currencyData: CurrencyData) {
        _sellCurrencyStateFlow.value = CurrencyExchangeState.Success(currencyData)
        exchangeCurrency()
    }

    fun onReceiveCurrencySelected(currencyData: CurrencyData) {
        _receiveCurrencyStateFlow.value = CurrencyExchangeState.Success(currencyData)
        exchangeCurrency()
    }

    private fun exchangeCurrency() {
        viewModelScope.launch {
            _receiveCurrencyBalanceStateFlow.value = interactor.exchangeCurrency(
                currencyExchangeData = currencyExchangeData,
                sellCurrencyBalance = (_sellCurrencyBalanceStateFlow.value as? CurrencyExchangeState.Success)?.value.toBigDecimal(),
                sellCurrency = (_sellCurrencyStateFlow.value as? CurrencyExchangeState.Success)?.value,
                receiveCurrency = (_receiveCurrencyStateFlow.value as? CurrencyExchangeState.Success)?.value
            )
        }
    }

    fun onSubmitClick() {
        viewModelScope.launch {
            val state: CurrencyExchangeState<String> = interactor.refreshBalance(
                balancesList = (_balancesDataListStateFlow.value as? CurrencyExchangeState.Success)?.value,
                sellCurrencyBalance = (_sellCurrencyBalanceStateFlow.value as? CurrencyExchangeState.Success)?.value.toBigDecimal(),
                receiveCurrencyBalance = (_receiveCurrencyBalanceStateFlow.value as? CurrencyExchangeState.Success)?.value,
                sellCurrency = (_sellCurrencyStateFlow.value as? CurrencyExchangeState.Success)?.value,
                receiveCurrency = (_receiveCurrencyStateFlow.value as? CurrencyExchangeState.Success)?.value
            )
            _refreshBalanceStateFlow.emit(state)
            _balancesDataListStateFlow.value = interactor.getBalancesList(currencyExchangeData)
        }
    }

    private fun convertReceiveCurrencyBalanceState(state: CurrencyExchangeState<BigDecimal>): CurrencyExchangeState<String> {
        return when (state) {
            is CurrencyExchangeState.Success -> CurrencyExchangeState.Success(
                convertReceiveCurrencyBalanceToString(state.value)
            )
            is CurrencyExchangeState.Error -> CurrencyExchangeState.Success("")
            is CurrencyExchangeState.Loading -> CurrencyExchangeState.Loading
            is CurrencyExchangeState.NoState -> CurrencyExchangeState.NoState
        }
    }

    private fun convertReceiveCurrencyBalanceToString(balance: BigDecimal): String {
        return when {
            balance.toDouble() == 0.0 -> "0.00"
            else -> "+${decimalFormatter.format(balance)}"
        }
    }

}