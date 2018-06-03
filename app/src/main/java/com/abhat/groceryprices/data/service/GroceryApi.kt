package com.abhat.groceryprices.data.service

import com.abhat.groceryprices.data.model.Grocery
import io.reactivex.Single
import retrofit2.http.GET

interface GroceryApi {

    @GET("RateList.aspx")
    fun fetchGroceryPrices(): Single<List<Grocery>>
}