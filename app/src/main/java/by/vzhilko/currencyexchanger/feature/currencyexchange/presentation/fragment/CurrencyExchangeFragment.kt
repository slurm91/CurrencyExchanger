package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.vzhilko.currencyexchanger.App
import by.vzhilko.currencyexchanger.databinding.FragmentCurrencyExchangeBinding
import by.vzhilko.currencyexchanger.feature.currencyexchange.di.CurrencyExchangeFragmentComponent
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.state.CurrencyExchangeState
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.util.showErrorDialog
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.util.showSuccessfulCurrencyExchangeDialog
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.viewmodel.CurrencyExchangeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrencyExchangeFragment : Fragment() {

    private var _binding: FragmentCurrencyExchangeBinding? = null
    private val binding: FragmentCurrencyExchangeBinding get() = _binding!!

    @Inject
    lateinit var viewModel: CurrencyExchangeViewModel

    override fun onAttach(context: Context) {
        val component: CurrencyExchangeFragmentComponent = getCurrencyExchangeFragmentComponent()
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyExchangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCurrencyExchangerView()
        initSubmitButton()
        subscribeInitializationStateFlowStateGetting()
        subscribeOnBalancesListStateGetting()
        //subscribeOnSellCurrencyStateGetting()
        subscribeOnSellCurrenciesListStateGetting()
        subscribeOnReceiveCurrenciesListStateGetting()
        subscribeOnReceiveCurrencyBalanceStateGetting()
        subscribeOnRefreshBalanceStateGetting()
    }

    private fun initCurrencyExchangerView() {
        binding.currencyExchangerView.onTextChangedAction = { balance ->
            viewModel.onSellCurrencyBalanceChanged(balance)
        }
        binding.currencyExchangerView.onSellCurrencySelectedAction = { currency ->
            viewModel.onSellCurrencySelected(currency)
        }
        binding.currencyExchangerView.onReceiveCurrencySelectedAction = { currency ->
            viewModel.onReceiveCurrencySelected(currency)
        }
    }

    private fun initSubmitButton() {
        binding.submitBtn.setOnClickListener {
            viewModel.onSubmitClick()
        }
    }

    private fun subscribeInitializationStateFlowStateGetting() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.initializationStateFlow.collectLatest { state ->
                    when(state) {
                        is CurrencyExchangeState.Error -> {
                            binding.progressBar.hideProgress()
                            showErrorDialog(
                                this@CurrencyExchangeFragment,
                                state.error.message ?: state.error.toString()
                            )
                        }
                        is CurrencyExchangeState.NoState -> {
                            binding.progressBar.hideProgress()
                        }
                        is CurrencyExchangeState.Loading -> {
                            binding.progressBar.showProgress()
                        }
                        is CurrencyExchangeState.Success -> {
                            binding.progressBar.hideProgress()
                            viewModel.startRefreshRates()
                        }
                    }
                }
            }
        }
    }

    private fun subscribeOnBalancesListStateGetting() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.balancesDataListStateFlow.collectLatest { state ->
                    when(state) {
                        is CurrencyExchangeState.Success -> {
                            binding.balancesListView.populate(state.value)
                        }
                        is CurrencyExchangeState.Error -> {
                            showErrorDialog(
                                this@CurrencyExchangeFragment,
                                state.error.message ?: state.error.toString()
                            )
                        }
                        is CurrencyExchangeState.Loading -> {}
                        is CurrencyExchangeState.NoState -> {}
                    }
                }
            }
        }
    }

    /*private fun subscribeOnSellCurrencyStateGetting() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sellCurrencyStateFlow.collectLatest { state ->
                    when(state) {
                        is CurrencyExchangeState.Success -> {
                            //binding.currencyExchangerView.populateSellCurrenciesList(state.value)
                            binding.currencyExchangerView.populateSellCurrency(state.value)
                        }
                        is CurrencyExchangeState.Error -> {}
                        is CurrencyExchangeState.Loading -> {}
                        is CurrencyExchangeState.NoState -> {}
                    }
                }
            }
        }
    }*/

    private fun subscribeOnSellCurrenciesListStateGetting() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sellCurrenciesListStateFlow.collectLatest { state ->
                    when(state) {
                        is CurrencyExchangeState.Success -> {
                            binding.currencyExchangerView.populateSellCurrenciesList(state.value)
                        }
                        is CurrencyExchangeState.Error -> {}
                        is CurrencyExchangeState.Loading -> {}
                        is CurrencyExchangeState.NoState -> {}
                    }
                }
            }
        }
    }

    private fun subscribeOnReceiveCurrenciesListStateGetting() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.receiveCurrenciesListStateFlow.collectLatest { state ->
                    when(state) {
                        is CurrencyExchangeState.Success -> {
                            binding.currencyExchangerView.populateReceiveCurrenciesList(state.value)
                        }
                        is CurrencyExchangeState.Error -> {}
                        is CurrencyExchangeState.Loading -> {}
                        is CurrencyExchangeState.NoState -> {}
                    }
                }
            }
        }
    }

    private fun subscribeOnReceiveCurrencyBalanceStateGetting() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.receiveCurrencyBalanceStateFlow.collectLatest { state ->
                    when(state) {
                        is CurrencyExchangeState.Success -> {
                            binding.currencyExchangerView.populateReceiveCurrencyBalance(state.value)
                        }
                        is CurrencyExchangeState.Error -> {
                            showErrorDialog(
                                this@CurrencyExchangeFragment,
                                state.error.message ?: state.error.toString()
                            )
                        }
                        is CurrencyExchangeState.Loading -> {}
                        is CurrencyExchangeState.NoState -> {}
                    }
                }
            }
        }
    }

    private fun subscribeOnRefreshBalanceStateGetting() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.refreshBalanceStateFlow.collectLatest { state ->
                    when(state) {
                        is CurrencyExchangeState.Success -> {
                            showSuccessfulCurrencyExchangeDialog(this@CurrencyExchangeFragment, state.value)
                        }
                        is CurrencyExchangeState.Error -> {
                            showErrorDialog(
                                this@CurrencyExchangeFragment,
                                state.error.message ?: state.error.toString()
                            )
                        }
                        is CurrencyExchangeState.Loading -> {}
                        is CurrencyExchangeState.NoState -> {}
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCurrencyExchangeFragmentComponent(): CurrencyExchangeFragmentComponent {
        return (requireActivity().applicationContext as App).appComponent
            .currencyExchangeFragmentComponentBuilder()
            .currencyExchangeFragment(this)
            .build()
    }

}