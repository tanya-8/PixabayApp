package com.example.practice_recycler_api

import com.google.firebase.appdistribution.gradle.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit=
        Retrofit
            .Builder()
            .baseUrl("https://pixabay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService( retrofit: Retrofit): RetrofitInterface=
        retrofit.create(RetrofitInterface::class.java)

    @Provides
    @Singleton
    fun provideLocationProvider(impl: DefaultLocationProvider): LocationProvider=impl

}