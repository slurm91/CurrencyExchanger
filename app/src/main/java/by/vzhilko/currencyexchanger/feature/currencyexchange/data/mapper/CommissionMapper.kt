package by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper

import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.CommissionEntity
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CommissionData
import javax.inject.Inject

class CommissionMapper @Inject constructor() {

    fun mapTo(data: CommissionEntity): CommissionData {
        return CommissionData(
            type = mapCommissionType(data.title),
            attemptsCount = data.attemptsCount,
            maxAttemptsCount = data.maxAttemptsCount
        )
    }

    fun mapFrom(data: CommissionData): CommissionEntity {
        return CommissionEntity(
            title = data.type.title,
            attemptsCount = data.attemptsCount,
            maxAttemptsCount = data.maxAttemptsCount
        )
    }

    private fun mapCommissionType(title: String): CommissionData.Type {
        return when(title) {
            CommissionData.Type.FIRST_FIVE_FREE.title -> CommissionData.Type.FIRST_FIVE_FREE
            else -> CommissionData.Type.UNKNOWN
        }
    }

}