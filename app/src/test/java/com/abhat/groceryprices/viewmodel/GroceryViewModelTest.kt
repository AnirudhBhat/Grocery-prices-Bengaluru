package com.abhat.groceryprices.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.abhat.groceryprices.data.model.Grocery
import com.abhat.groceryprices.data.repository.GroceryPricesRepositoryImpl
import com.abhat.groceryprices.presentation.groceryPrices.GroceryViewModel
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.junit.rules.TestRule
import org.junit.Rule



@RunWith(JUnit4::class)
class GroceryViewModelTest {

    private lateinit var groceryViewModel: GroceryViewModel

    @Mock
    lateinit var repository: GroceryPricesRepositoryImpl

    @Mock lateinit var observer: Observer<List<Grocery>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        repository = mock()
        observer = mock()
        groceryViewModel = GroceryViewModel(repository)
    }

    @Test
    fun `fetch when observed`() {
        whenever(repository.fetchGroceryPrices()).thenReturn(Single.just(listOf()))
        groceryViewModel.fetchGroceryPrices()
        groceryViewModel.getResult().observeForever(observer)
        verify(repository).fetchGroceryPrices()
    }

    @Test
    fun `don't fetch result if result is not null`() {
        val groceryList = listOf(Grocery("", ""))
        whenever(repository.fetchGroceryPrices()).thenReturn(Single.just(groceryList))
        groceryViewModel.result.observeForever(observer)
        groceryViewModel.result.value = groceryList
        verify(observer).onChanged(groceryList)
        groceryViewModel.fetchGroceryPrices()
        verifyNoMoreInteractions(repository)
    }

}