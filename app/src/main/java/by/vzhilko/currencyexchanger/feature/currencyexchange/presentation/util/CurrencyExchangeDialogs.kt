package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.vzhilko.currencyexchanger.R
import by.vzhilko.currencyexchanger.core.ui.dialog.DEFAULT_DIALOG_MODEL_TAG
import by.vzhilko.currencyexchanger.core.ui.dialog.DefaultDialogModel
import by.vzhilko.currencyexchanger.core.util.navigateSafe

fun showSuccessfulCurrencyExchangeDialog(fragment: Fragment, message: String) {
    val model = DefaultDialogModel(
        title = fragment.requireContext().getString(R.string.currency_exchanger_title),
        message = message,
        positiveButtonText = fragment.requireContext().getString(R.string.ok_caption)
    )

    val bundle = Bundle().apply {
        putParcelable(DEFAULT_DIALOG_MODEL_TAG, model)
    }

    fragment.findNavController().navigateSafe(
        R.id.action_currencyExchangeFragment_to_defaultDialogFragment,
        bundle
    )
}

fun showErrorDialog(fragment: Fragment, message: String) {
    val model = DefaultDialogModel(
        title = fragment.requireContext().getString(R.string.app_name),
        message = message,
        positiveButtonText = fragment.requireContext().getString(R.string.ok_caption)
    )

    val bundle = Bundle().apply {
        putParcelable(DEFAULT_DIALOG_MODEL_TAG, model)
    }

    fragment.findNavController().navigateSafe(
        R.id.action_currencyExchangeFragment_to_defaultDialogFragment,
        bundle
    )
}