package com.andro.indieschool.cocktailapp

import android.app.Application
import com.andro.indieschool.cocktailapp.di.NetworkModule
import com.andro.indieschool.cocktailapp.di.PersistenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CocktailApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CocktailApp)
            modules(
                NetworkModule.getNetworkModule(BuildConfig.DEBUG, BuildConfig.BASE_URL),
                PersistenceModule.module
            )
        }
    }

}