package com.example.catfacts.di

import com.example.catfacts.data.network.services.CatFactsService
import com.example.catfacts.data.network.services.CatImageService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CAT_FACT_BASE_URL = "https://cat-fact.herokuapp.com/"
    private const val CAT_IMAGE_BASE_URL = "https://api.thecatapi.com/"

    private fun getDefaultJson() = Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(getDefaultJson())
            .build()
    }

    @Provides
    @Singleton
    @Named("CatFact")
    fun provideCatFactRetrofit(client: OkHttpClient): Retrofit {
        return provideRetrofit(client, CAT_FACT_BASE_URL)
    }

    @Provides
    @Singleton
    @Named("CatImage")
    fun provideCatImageRetrofit(client: OkHttpClient): Retrofit {
        return provideRetrofit(client, CAT_IMAGE_BASE_URL)
    }

    @Provides
    fun provideCatFactService(@Named("CatFact") retrofit: Retrofit): CatFactsService {
        return retrofit.create(CatFactsService::class.java)
    }

    @Provides
    fun provideCatImageService(@Named("CatImage") retrofit: Retrofit): CatImageService {
        return retrofit.create(CatImageService::class.java)
    }
}
