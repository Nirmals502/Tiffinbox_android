package com.example.retrofi_implementation_.di

import com.example.retrofi_implementation_.Retrofit.FakerApi
import com.example.retrofi_implementation_.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Singleton
    @Provides
    fun providesFakerApi(retrofit: Retrofit): FakerApi {
        return retrofit.create(FakerApi::class.java)
    }

}