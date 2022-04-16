package com.andro.indieschool.cocktailapp.di

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object NetworkModule {

    private const val MAX_CACHE_SIZE = 30 * 1024 * 1024
    private const val TIMEOUT_DURATION = 15L

    fun getNetworkModule(isDebugMode: Boolean, baseUrl: String): Module {
        return module {
            single<Cache> { provideNetworkCache(androidContext()) }
            single<HttpLoggingInterceptor> { providesLogInterceptor(isDebugMode = isDebugMode) }
            single<OkHttpClient> {
                providesOkHttpClient(
                    cache = get<Cache>(),
                    loggingInterceptor = get<HttpLoggingInterceptor>()
                )
            }
            single<Retrofit> {
                providesRetrofit(
                    okHttpClient = get<OkHttpClient>(),
                    baseUrl = baseUrl
                )
            }
        }
    }

    private fun provideNetworkCache(context: Context): Cache {
        val cacheDir = context.externalCacheDir ?: context.cacheDir
        val cacheFile = File(cacheDir, "OkHTTPCache")
        return Cache(cacheFile, MAX_CACHE_SIZE.toLong())
    }

    private fun providesLogInterceptor(isDebugMode: Boolean) = HttpLoggingInterceptor().apply {
        level = if (isDebugMode) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private fun providesOkHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(cache)
            addInterceptor(loggingInterceptor)
            connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
            readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
        }.build()
    }

    private fun providesRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }
}