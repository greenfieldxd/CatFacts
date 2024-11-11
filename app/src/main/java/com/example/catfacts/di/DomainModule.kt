package com.example.catfacts.di

import com.example.catfacts.data.repository.FactRepository
import com.example.catfacts.data.repository.ImageRepository
import com.example.catfacts.domain.usecases.GetCardsUseCase
import com.example.catfacts.domain.usecases.GetCardsUseCaseImpl
import com.example.catfacts.domain.usecases.LoadCatFactsUseCase
import com.example.catfacts.domain.usecases.LoadCatFactsUseCaseImpl
import com.example.catfacts.domain.usecases.LoadCatImagesUseCase
import com.example.catfacts.domain.usecases.LoadCatImagesUseCaseImpl
import com.example.catfacts.domain.usecases.LoadNewCardsUseCase
import com.example.catfacts.domain.usecases.LoadNewCardsUseCaseImpl
import com.example.catfacts.presentation.mappers.FactAndImageToCatCardMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideLoadCatFactsUseCase(factRepository: FactRepository): LoadCatFactsUseCase {
        return LoadCatFactsUseCaseImpl(factRepository)
    }

    @Provides
    @Singleton
    fun provideLoadCatImagesUseCase(imageRepository: ImageRepository): LoadCatImagesUseCase {
        return LoadCatImagesUseCaseImpl(imageRepository)
    }

    @Provides
    @Singleton
    fun provideGetCardsUseCase(loadCatFactsUseCase: LoadCatFactsUseCase,
                               loadCatImagesUseCase: LoadCatImagesUseCase,
                               factAndImageToCatCardMapper: FactAndImageToCatCardMapper)
    : GetCardsUseCase {
        return GetCardsUseCaseImpl(loadCatFactsUseCase, loadCatImagesUseCase,factAndImageToCatCardMapper)
    }

    @Provides
    @Singleton
    fun provideRefreshCardsUseCase(
        factRepository: FactRepository,
        imageRepository: ImageRepository
    ) : LoadNewCardsUseCase {
        return LoadNewCardsUseCaseImpl(factRepository, imageRepository)
    }
}