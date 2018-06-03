package com.abhat.groceryprices

import android.app.Application
import android.content.Context
import com.abhat.groceryprices.di.components.DaggerDataComponent
import com.abhat.groceryprices.di.components.DataComponent
import com.abhat.groceryprices.di.module.AppModule
import com.abhat.groceryprices.di.module.DataModule
import com.abhat.groceryprices.utils.Constants

class App: Application() {
    lateinit var dataComponent: DataComponent

    companion object {
        private lateinit var context: App

        fun getContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        this.initializeInjector()
    }

    private fun initializeInjector() {
        dataComponent = DaggerDataComponent.builder()
                .appModule(AppModule(this))
                .dataModule(DataModule(Constants.URL))
                .build()
    }
}