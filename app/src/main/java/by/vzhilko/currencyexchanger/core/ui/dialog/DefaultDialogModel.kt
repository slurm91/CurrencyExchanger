package by.vzhilko.currencyexchanger.core.ui.dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DefaultDialogModel(
    val title: String,
    val message: String,
    val positiveButtonText: String
) : Parcelable