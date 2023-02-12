package by.vzhilko.currencyexchanger.core.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController

fun NavController.navigateSafe(@IdRes action: Int, bundle: Bundle) {
    val destinationId: Int? = currentDestination?.getAction(action)?.destinationId
    destinationId?.let { navigate(it, bundle) }
}