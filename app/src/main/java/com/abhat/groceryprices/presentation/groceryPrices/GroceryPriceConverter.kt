package com.abhat.groceryprices.presentation.groceryPrices

import android.util.Log
import com.abhat.groceryprices.data.model.Grocery
import okhttp3.ResponseBody
import retrofit2.Converter
import org.jsoup.Jsoup
import retrofit2.Retrofit
import java.lang.reflect.Type


class GroceryPriceConverter: Converter<ResponseBody, List<Grocery>> {

    private lateinit var vegetable: ArrayList<Grocery>
    companion object {
        val INSTANCE = GroceryPriceConverter()
    }

    override fun convert(value: ResponseBody?): List<Grocery> {
        return groceryPrices()
    }

    class Factory : Converter.Factory() {

        override fun responseBodyConverter(type: Type?,
                                           annotations: Array<Annotation>?,
                                           retrofit: Retrofit?): Converter<ResponseBody, *>? {
            return INSTANCE
        }

    }

    private fun groceryPrices(): List<Grocery> {
        val url = "http://www.hopcoms.kar.nic.in/RateList.aspx"
        vegetable = ArrayList<Grocery>()
        //price = ArrayList<String>()
        try {
            val doc = Jsoup.connect(url).get()
            var j = 2
            for (i in 0..119) {
                val elem = doc.select("span#ctl00_LC_grid1_ctl" + (if (j < 10) 0.toString() + "" + j else j) + "_Label" + 1)
                val elem1 = doc.select("span#ctl00_LC_grid1_ctl" + (if (j < 10) 0.toString() + "" + j else j) + "_Label" + 2)
                val elem2 = doc.select("span#ctl00_LC_grid1_ctl" + (if (j < 10) 0.toString() + "" + j else j) + "_Label" + 3)
                val elem3 = doc.select("span#ctl00_LC_grid1_ctl" + (if (j < 10) 0.toString() + "" + j else j) + "_Label" + 4)
                vegetable.add(Grocery(elem[0].text(), elem1[0].text()))
                vegetable.add(Grocery(elem2[0].text(), elem3[0].text()))
                j++
            }

        } catch (e: Exception) {
            //e.printStackTrace();
            for (k in 0 until vegetable.size) {
                Log.d("VEGETABLES", "Vegetable: " + vegetable.get(k) + "   Price: ")
            }
            return vegetable
        }
        return vegetable

    }
}