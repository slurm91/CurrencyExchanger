package by.vzhilko.currencyexchanger

import android.app.Application
import by.vzhilko.currencyexchanger.core.di.component.AppComponent
import by.vzhilko.currencyexchanger.core.di.component.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }

}