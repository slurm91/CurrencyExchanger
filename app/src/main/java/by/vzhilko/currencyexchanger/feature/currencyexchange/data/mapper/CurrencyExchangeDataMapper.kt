package by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper

import by.vzhilko.currencyexchanger.feature.currencyexchange.data.dto.CurrencyExchangeDto
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.dto.CurrencyRatesDto
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyExchangeData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyRatesData
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class CurrencyExchangeDataMapper @Inject constructor(
    private val currencyDataMapper: CurrencyDataMapper,
    private val currencyRatesDataMapper: CurrencyRatesDataMapper,
) {

    fun map(data: CurrencyExchangeDto): CurrencyExchangeData {
        return CurrencyExchangeData(
            baseCurrency = currencyDataMapper.map(data.base),
            currencyRatesList = mapCurrencyRatesDataList(data.currencyRates)
        )
    }

    @Suppress("NO_REFLECTION_IN_CLASS_PATH", "UNCHECKED_CAST")
    private fun mapCurrencyRatesDataList(data: CurrencyRatesDto): List<CurrencyRatesData> {
        val currencyRatesDtoKClass: KClass<CurrencyRatesDto> = data::class as KClass<CurrencyRatesDto>

        //Temporary decision. Just a hack to convert all fields in CurrencyRatesDto to List<CurrencyRatesData>.
        return currencyRatesDtoKClass.members
            .filterIsInstance<KProperty1<*, *>>()
            .map { property ->
                currencyRatesDataMapper.map(
                    currencyName = property.name.uppercase(),
                    rate = property.call(data) as Double
                )
            }
    }

}