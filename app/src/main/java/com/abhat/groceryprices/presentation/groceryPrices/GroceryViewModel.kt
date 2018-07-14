package com.abhat.groceryprices.presentation.groceryPrices

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.abhat.groceryprices.data.model.Grocery
import com.abhat.groceryprices.data.repository.GroceryPricesRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GroceryViewModel @Inject constructor(
        private val groceryPricesRepository: GroceryPricesRepositoryImpl): ViewModel(), LifecycleObserver {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var result: MutableLiveData<List<Grocery>> = MutableLiveData()

    fun fetchGroceryPrices() {
        result.value?.let {
            // do nothing if we already have result
        } ?: run {
            compositeDisposable.add(groceryPricesRepository.fetchGroceryPrices()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        result.value = it
                    }, {
                        result.value = listOf()
                    }))
        }
    }

    fun getResult(): LiveData<List<Grocery>> {
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}