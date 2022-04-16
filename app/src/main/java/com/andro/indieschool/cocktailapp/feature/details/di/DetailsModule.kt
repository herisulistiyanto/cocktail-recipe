package com.andro.indieschool.cocktailapp.feature.details.di

import com.andro.indieschool.cocktailapp.feature.details.data.DrinkDetailsRepositoryImpl
import com.andro.indieschool.cocktailapp.feature.details.data.local.DrinkDetailsLocalDataSource
import com.andro.indieschool.cocktailapp.feature.details.data.remote.DrinkDetailsRemoteDataSource
import com.andro.indieschool.cocktailapp.feature.details.data.remote.DrinkDetailsWebService
import com.andro.indieschool.cocktailapp.feature.details.domain.DrinkDetailsRepository
import com.andro.indieschool.cocktailapp.feature.details.presentation.ui.drink.DrinkDetailsViewModel
import com.andro.indieschool.cocktailapp.db.dao.DrinkDetailDao
import com.andro.indieschool.cocktailapp.db.AppDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

object DetailsModule {

    val module = module {
        single<DrinkDetailsWebService> {
            providesDrinkDetailsWebService(retrofit = get<Retrofit>())
        }
        single<DrinkDetailsRemoteDataSource> {
            DrinkDetailsRemoteDataSource(service = get<DrinkDetailsWebService>())
        }
        single<DrinkDetailDao> {
            providesDrinkDetailDao(db = get<AppDatabase>())
        }
        single<DrinkDetailsLocalDataSource> {
            DrinkDetailsLocalDataSource(dao = get<DrinkDetailDao>())
        }
        factory <DrinkDetailsRepository> {
            DrinkDetailsRepositoryImpl(
                remoteDataSource = get<DrinkDetailsRemoteDataSource>(),
                localDataSource = get<DrinkDetailsLocalDataSource>()
            )
        } bind DrinkDetailsRepository::class

        viewModel {
            DrinkDetailsViewModel(repository = get<DrinkDetailsRepository>())
        }
    }

    private fun providesDrinkDetailsWebService(
        retrofit: Retrofit
    ): DrinkDetailsWebService {
        return retrofit.create(DrinkDetailsWebService::class.java)
    }

    private fun providesDrinkDetailDao(db: AppDatabase): DrinkDetailDao {
        return db.drinkDetailDao()
    }
}