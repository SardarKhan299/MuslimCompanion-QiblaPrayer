package com.qibla.qiblacompass.prayertime.finddirection.di

import com.qibla.qiblacompass.prayertime.finddirection.common.Constants
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.ApiInterface
import com.qibla.qiblacompass.prayertime.finddirection.data.repository.MainRepositoryImpl
import com.qibla.qiblacompass.prayertime.finddirection.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQiblaApi():ApiInterface{

        val logging =  HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val  httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(100, TimeUnit.SECONDS).readTimeout(100, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun returnREpository(api:ApiInterface):MainRepository{
        return MainRepositoryImpl(api)
    }

}