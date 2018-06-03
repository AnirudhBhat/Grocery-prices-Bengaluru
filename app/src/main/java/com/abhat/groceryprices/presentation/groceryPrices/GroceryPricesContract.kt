package com.abhat.groceryprices.presentation.groceryPrices

import com.abhat.groceryprices.data.model.Grocery

interface GroceryPricesContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showGroceryPricesList(groceryPricesList: List<Grocery>)
        fun showError(error: Throwable)
    }

    interface Presenter {
        fun fetchGroceryPrices()
    }
}