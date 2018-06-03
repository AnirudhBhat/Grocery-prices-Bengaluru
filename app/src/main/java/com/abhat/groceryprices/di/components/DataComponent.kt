package com.abhat.groceryprices.di.components

import com.abhat.groceryprices.di.module.AppModule
import com.abhat.groceryprices.di.module.DataModule
import com.abhat.groceryprices.di.modules.GroceryRepositoryModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Anirudh Uppunda on 03/06/18.
 */
@Singleton
@Component(modules = [DataModule::class, AppModule::class])
interface DataComponent {
    fun plus(groceryRepositoryModule: GroceryRepositoryModule): GroceryComponent
}