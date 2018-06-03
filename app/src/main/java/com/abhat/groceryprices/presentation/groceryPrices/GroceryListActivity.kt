package com.abhat.groceryprices.presentation.groceryPrices

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.abhat.groceryprices.App
import com.abhat.groceryprices.R
import com.abhat.groceryprices.data.model.Grocery
import com.abhat.groceryprices.di.modules.GroceryRepositoryModule
import javax.inject.Inject

class GroceryListActivity: AppCompatActivity(), GroceryPricesContract.View {

    @Inject
    lateinit var groceryViewModelFactory: GroceryViewModelFactory

    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }
    private val groceryRecyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.groceries_recycler_view) }
    private val groceryAdapter: GroceryAdapter by lazy { GroceryAdapter(this, listOf()) }
    private val layoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).dataComponent
                .plus(GroceryRepositoryModule(this))
                .inject(this)
        setupRecyclerView()
        showLoading()
        setupViewModel()
    }

    private fun setupViewModel() {
        val groceryViewModel = ViewModelProviders.of(this, groceryViewModelFactory).get(GroceryViewModel::class.java)
        groceryViewModel.getResult().observe(this, Observer<List<Grocery>> { t -> processResponse(t) })
        lifecycle.addObserver(groceryViewModel)
        groceryViewModel.fetchGroceryPrices()
    }

    private fun processResponse(groceryPricesList: List<Grocery>?) {
        groceryPricesList?.let {
            hideLoading()
            showGroceryPricesList(groceryPricesList)
        }
        hideLoading()
    }

    private fun setupRecyclerView() {
        groceryRecyclerView.layoutManager = layoutManager
        groceryRecyclerView.adapter = groceryAdapter
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showGroceryPricesList(groceryPricesList: List<Grocery>) {
        groceryAdapter.setGroceryList(groceryPricesList)
        groceryAdapter.notifyDataSetChanged()
    }

    override fun showError(error: Throwable) {
        error.printStackTrace()
    }


}