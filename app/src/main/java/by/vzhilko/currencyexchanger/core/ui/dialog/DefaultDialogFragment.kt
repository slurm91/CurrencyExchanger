package by.vzhilko.currencyexchanger.core.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import by.vzhilko.currencyexchanger.R

const val DEFAULT_DIALOG_MODEL_TAG: String = "DEFAULT_DIALOG_MODEL_TAG"

class DefaultDialogFragment : DialogFragment() {

    private var model: DefaultDialogModel? = null
    var onPositiveButtonClickAction: (() -> Unit)? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = arguments?.getParcelable(DEFAULT_DIALOG_MODEL_TAG) as? DefaultDialogModel
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(model?.title ?: getString(R.string.app_name))
            .setMessage(model?.message ?: "")
            .setPositiveButton(
                model?.positiveButtonText ?: getString(R.string.ok_caption)
            ) { dialog, which -> onPositiveButtonClickAction?.invoke() }
            .create()
    }

}