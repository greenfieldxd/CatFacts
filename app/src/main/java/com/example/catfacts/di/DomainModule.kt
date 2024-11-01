package com.example.catfacts.di

import com.example.catfacts.data.repository.FactRepository
import com.example.catfacts.data.repository.ImageRepository
import com.example.catfacts.domain.models.usecases.GetCardsUseCase
import com.example.catfacts.domain.models.usecases.GetCardsUseCaseImpl
import com.example.catfacts.domain.models.usecases.LoadCatFactsUseCase
import com.example.catfacts.domain.models.usecases.LoadCatFactsUseCaseImpl
import com.example.catfacts.domain.models.usecases.LoadCatImagesUseCase
import com.example.catfacts.domain.models.usecases.LoadCatImagesUseCaseImpl
import com.example.catfacts.domain.models.usecases.RefreshCardsUseCase
import com.example.catfacts.domain.models.usecases.RefreshCardsUseCaseImpl
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
        imageRepository: ImageRepository,
        factAndImageToCatCardMapper : FactAndImageToCatCardMapper
    ) : RefreshCardsUseCase {
        return RefreshCardsUseCaseImpl(factRepository, imageRepository, factAndImageToCatCardMapper)
    }
}