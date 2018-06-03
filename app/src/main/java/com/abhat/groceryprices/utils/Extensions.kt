package com.abhat.groceryprices.utils

import android.content.Context
import android.net.ConnectivityManager
import com.abhat.groceryprices.App


/**
 * Created by Anirudh Uppunda on 03/06/18.
 */
fun Context.isOnline(): Boolean {
    val connectivityManager = App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
}