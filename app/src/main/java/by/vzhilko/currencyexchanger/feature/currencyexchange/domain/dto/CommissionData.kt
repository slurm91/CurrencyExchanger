package by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto

data class CommissionData(
    val type: Type,
    val attemptsCount: Int,
    val maxAttemptsCount: Int
) {

    enum class Type(val title: String) {
        FIRST_FIVE_FREE("FirstFiveFree"),
        UNKNOWN("")
    }

}