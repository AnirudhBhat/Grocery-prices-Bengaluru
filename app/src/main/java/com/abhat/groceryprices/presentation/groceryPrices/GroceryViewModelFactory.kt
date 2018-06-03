package com.abhat.groceryprices.presentation.groceryPrices

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.abhat.groceryprices.data.repository.GroceryPricesRepositoryImpl
import javax.inject.Inject

class GroceryViewModelFactory @Inject constructor(
        private val repository: GroceryPricesRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
            return GroceryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}