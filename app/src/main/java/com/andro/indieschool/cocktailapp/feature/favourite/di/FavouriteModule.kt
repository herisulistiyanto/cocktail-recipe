package com.andro.indieschool.cocktailapp.feature.favourite.di

import com.andro.indieschool.cocktailapp.db.AppDatabase
import com.andro.indieschool.cocktailapp.db.dao.DrinkFavouriteDao
import com.andro.indieschool.cocktailapp.feature.favourite.data.DrinkFavouriteRepositoryImpl
import com.andro.indieschool.cocktailapp.feature.favourite.data.local.DrinkFavouriteLocalDataSource
import com.andro.indieschool.cocktailapp.feature.favourite.domain.DrinkFavouriteRepository
import com.andro.indieschool.cocktailapp.feature.favourite.presentation.ui.FavouriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object FavouriteModule {

    val module = module {
        single<DrinkFavouriteDao> {
            provideFavouriteDao(appDatabase = get<AppDatabase>())
        }
        single<DrinkFavouriteLocalDataSource> {
            DrinkFavouriteLocalDataSource(dao = get<DrinkFavouriteDao>())
        }
        factory<DrinkFavouriteRepository> {
            DrinkFavouriteRepositoryImpl(localDataSource = get<DrinkFavouriteLocalDataSource>())
        } bind DrinkFavouriteRepository::class
        viewModel {
            FavouriteViewModel(repository = get<DrinkFavouriteRepository>())
        }
    }

    private fun provideFavouriteDao(appDatabase: AppDatabase): DrinkFavouriteDao {
        return appDatabase.drinkFavouriteDao()
    }

}