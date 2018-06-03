package com.abhat.groceryprices.data.repository

import com.abhat.groceryprices.data.model.Grocery
import io.reactivex.Single

interface GroceryPricesRepository {
    fun fetchGroceryPrices(): Single<List<Grocery>>
}