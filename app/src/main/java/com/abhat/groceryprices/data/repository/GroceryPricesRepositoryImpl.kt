package com.abhat.groceryprices.data.repository

import com.abhat.groceryprices.data.model.Grocery
import com.abhat.groceryprices.data.service.GroceryApi
import io.reactivex.Single

class GroceryPricesRepositoryImpl(private val groceryApi: GroceryApi): GroceryPricesRepository {

    override fun fetchGroceryPrices(): Single<List<Grocery>> {
        return groceryApi.fetchGroceryPrices()
    }
}