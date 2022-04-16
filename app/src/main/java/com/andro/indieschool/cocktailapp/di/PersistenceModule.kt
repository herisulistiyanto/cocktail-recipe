package com.andro.indieschool.cocktailapp.di

import android.content.Context
import androidx.room.Room
import com.andro.indieschool.cocktailapp.db.dao.DrinkDao
import com.andro.indieschool.cocktailapp.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object PersistenceModule {

    val module = module {
        single<AppDatabase> { provideAppDatabase(androidApplication()) }
    }

    private fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "cocktail.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}