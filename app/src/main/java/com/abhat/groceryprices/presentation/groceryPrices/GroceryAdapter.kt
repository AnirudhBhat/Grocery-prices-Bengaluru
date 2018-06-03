package com.abhat.groceryprices.presentation.groceryPrices

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.abhat.groceryprices.R
import com.abhat.groceryprices.data.model.Grocery


class GroceryAdapter(private val context: Context,
                     private var groceryPricesList: List<Grocery>): RecyclerView.Adapter<GroceryAdapter.MyViewHolder>() {


    fun setGroceryList(groceryPricesList: List<Grocery>) {
        this.groceryPricesList = groceryPricesList
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder{
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.grocery_single_row, parent, false);
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groceryPricesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val groceryName = groceryPricesList[position].name
        val groceryPrice = groceryPricesList[position].price
        holder.bind(groceryName, groceryPrice)
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val groceryNameText = view.findViewById<TextView>(R.id.groceryName)
        val groceryPriceText = view.findViewById<TextView>(R.id.groceryPrice)

        fun bind(groceryName: String, groceryPrice: String) {
            groceryNameText.text = groceryName
            groceryPriceText.text = groceryPrice
        }
    }
}