package com.abhat.groceryprices.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Anirudh Uppunda on 03/06/18.
 */
@Module
internal class AppModule(private val context: Context) {

    @Provides
    internal fun provideContext() = context

}