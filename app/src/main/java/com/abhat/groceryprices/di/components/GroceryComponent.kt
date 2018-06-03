package com.abhat.groceryprices.di.components

import com.abhat.groceryprices.di.modules.GroceryRepositoryModule
import com.abhat.groceryprices.presentation.groceryPrices.GroceryListActivity
import dagger.Subcomponent

@Subcomponent(modules = [GroceryRepositoryModule::class])
interface GroceryComponent {
    fun inject(activity: GroceryListActivity)
}