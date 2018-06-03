package com.abhat.groceryprices.di.modules

import com.abhat.groceryprices.data.repository.GroceryPricesRepositoryImpl
import com.abhat.groceryprices.data.service.GroceryApi
import com.abhat.groceryprices.presentation.groceryPrices.GroceryListActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class GroceryRepositoryModule(private var view: GroceryListActivity) {

    @Provides
    fun providesView() = view

    @Provides
    fun provideGroceryRepository(groceryApi: GroceryApi) = GroceryPricesRepositoryImpl(groceryApi)

    @Provides
    fun provideGroceryApi(retrofit: Retrofit) = retrofit.create(GroceryApi::class.java)
}