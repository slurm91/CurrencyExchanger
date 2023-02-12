package by.vzhilko.currencyexchanger.feature.currencyexchange.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import by.vzhilko.currencyexchanger.core.datasource.database.room.AppRoomDatabase
import by.vzhilko.currencyexchanger.core.datasource.network.retrofit.createApi
import by.vzhilko.currencyexchanger.core.di.annotation.coroutine.Default
import by.vzhilko.currencyexchanger.core.di.annotation.coroutine.IO
import by.vzhilko.currencyexchanger.core.di.annotation.scope.FragmentScope
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.dao.BalanceDao
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.api.CurrencyExchangeServiceApi
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.dao.CommissionDao
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper.BalanceDataMapper
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper.CommissionMapper
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper.CurrencyExchangeDataMapper
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.repository.CurrencyExchangeRepository
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.interactor.CurrencyExchangeInteractor
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.repository.ICurrencyExchangeRepository
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.fragment.CurrencyExchangeFragment
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.viewmodel.CurrencyExchangeViewModel
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.viewmodel.CurrencyExchangeViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
class CurrencyExchangeFragmentModule {

    @FragmentScope
    @Provides
    fun provideCurrencyExchangeViewModel(
        fragment: CurrencyExchangeFragment,
        interactor: CurrencyExchangeInteractor,
    ): CurrencyExchangeViewModel {
        val viewModelFactoryFactory = CurrencyExchangeViewModelFactory(interactor)
        return ViewModelProvider(
            owner = fragment,
            factory = viewModelFactoryFactory
        )[CurrencyExchangeViewModel::class.java]
    }

    @Provides
    fun provideCurrencyExchangeInteractor(
        repository: ICurrencyExchangeRepository,
        @IO ioDispatcher: CoroutineDispatcher,
        @Default defaultDispatcher: CoroutineDispatcher
    ): CurrencyExchangeInteractor {
        return CurrencyExchangeInteractor(repository, ioDispatcher, defaultDispatcher)
    }

    @Provides
    fun provideCurrencyExchangeRepository(
        context: Context,
        serviceApi: CurrencyExchangeServiceApi,
        balanceDao: BalanceDao,
        commissionDao: CommissionDao,
        currencyExchangeDataMapper: CurrencyExchangeDataMapper,
        balanceDataMapper: BalanceDataMapper,
        commissionMapper: CommissionMapper
    ): ICurrencyExchangeRepository {
        return CurrencyExchangeRepository(
            context,
            serviceApi,
            balanceDao,
            commissionDao,
            currencyExchangeDataMapper,
            balanceDataMapper,
            commissionMapper
        )
    }

    @Provides
    fun provideCurrencyExchangeServiceApi(retrofit: Retrofit): CurrencyExchangeServiceApi {
        return retrofit.createApi()
    }

    @Provides
    fun provideBalancesDao(database: AppRoomDatabase): BalanceDao {
        return database.balanceDao()
    }

    @Provides
    fun provideCommissionDao(database: AppRoomDatabase): CommissionDao {
        return database.commissionDao()
    }

}