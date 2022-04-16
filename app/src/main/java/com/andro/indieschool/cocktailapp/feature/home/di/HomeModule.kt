package com.andro.indieschool.cocktailapp.feature.home.di

import com.andro.indieschool.cocktailapp.db.AppDatabase
import com.andro.indieschool.cocktailapp.feature.home.data.local.HomeLocalDataSource
import com.andro.indieschool.cocktailapp.db.dao.DrinkDao
import com.andro.indieschool.cocktailapp.feature.home.data.remote.HomeNetworkService
import com.andro.indieschool.cocktailapp.feature.home.data.remote.HomeRemoteDataSource
import com.andro.indieschool.cocktailapp.feature.home.data.repository.HomeRepositoryImpl
import com.andro.indieschool.cocktailapp.feature.home.domain.repository.HomeRepository
import com.andro.indieschool.cocktailapp.feature.home.presentation.ui.alcohol.AlcoholViewModel
import com.andro.indieschool.cocktailapp.feature.home.presentation.ui.home.HomeViewModel
import com.andro.indieschool.cocktailapp.feature.home.presentation.ui.nonalcohol.NonAlcoholViewModel
import com.andro.indieschool.cocktailapp.feature.home.presentation.ui.optional.OptionalAlcoholViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

object HomeModule {

    val module = module {
        single<HomeNetworkService> { providesHomeNetworkService(retrofit = get<Retrofit>()) }
        single<HomeRemoteDataSource> { HomeRemoteDataSource(homeNetworkService = get<HomeNetworkService>()) }
        single<DrinkDao> { providesDrinkDao(db = get<AppDatabase>()) }
        single<HomeLocalDataSource> { HomeLocalDataSource(drinkDao = get<DrinkDao>()) }
        factory<HomeRepository> {
            HomeRepositoryImpl(
                remoteDataSource = get<HomeRemoteDataSource>(),
                localDataSource = get<HomeLocalDataSource>()
            )
        } bind HomeRepository::class

        viewModel<HomeViewModel> { HomeViewModel(repository = get<HomeRepository>()) }
        viewModel<NonAlcoholViewModel> { NonAlcoholViewModel(repository = get<HomeRepository>()) }
        viewModel<AlcoholViewModel> { AlcoholViewModel(repository = get<HomeRepository>()) }
        viewModel<OptionalAlcoholViewModel> { OptionalAlcoholViewModel(repository = get<HomeRepository>()) }
    }

    private fun providesHomeNetworkService(retrofit: Retrofit): HomeNetworkService {
        return retrofit.create<HomeNetworkService>()
    }

    private fun providesDrinkDao(db: AppDatabase): DrinkDao = db.drinkDao()
}