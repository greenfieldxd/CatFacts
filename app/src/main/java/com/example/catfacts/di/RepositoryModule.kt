package com.example.catfacts.di

import com.example.catfacts.data.repository.CatFactRepositoryImpl
import com.example.catfacts.data.repository.CatImageRepositoryImpl
import com.example.catfacts.data.repository.FactRepository
import com.example.catfacts.data.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFactRepository(impl: CatFactRepositoryImpl): FactRepository

    @Binds
    abstract fun bindImageRepository(impl: CatImageRepositoryImpl): ImageRepository
}