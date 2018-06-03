package com.abhat.groceryprices.di.module

import android.content.Context
import com.abhat.groceryprices.presentation.groceryPrices.GroceryPriceConverter
import com.abhat.groceryprices.utils.isOnline
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Anirudh Uppunda on 03/06/18.
 */
@Module
internal class DataModule(private val baseUrl: String) {

    companion object {
        private val CACHE_SIZE = 10 * 1024 * 1024 * 1L // 10 MB
    }

    @Provides
    @Singleton
    internal fun provideHttpCache(context: Context) = Cache(context.cacheDir, CACHE_SIZE)

    @Provides
    @Singleton
    fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            // re-write response header to force use of cache
            val cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.MINUTES)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .removeHeader("Pragma")
                    .build()
        }
    }

    @Provides
    @Singleton
    fun provideOfflineCacheInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            if (!context.isOnline()) {
                val cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()

                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .removeHeader("Pragma")
                        .build()
            }

            chain.proceed(request)
        }
    }


    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache, context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logging)
                .addInterceptor(provideOfflineCacheInterceptor(context))
                .addNetworkInterceptor(provideCacheInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
            .addConverterFactory(GroceryPriceConverter.Factory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    
}