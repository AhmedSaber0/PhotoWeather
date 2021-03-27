package com.rebusta.photoweather.di

import com.rebusta.photoweather.data.remote.WeatherApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { provideOkHttpClient(httpLoggingInterceptor = get()) }
    factory { provideApiService(retrofit = get()) }
    single { provideRetrofit(okHttpClient = get()) }
    single { provideLoggingInterceptor() }
}


fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("http://api.openweathermap.org/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
}

fun provideApiService(retrofit: Retrofit): WeatherApiService =
    retrofit.create(WeatherApiService::class.java)

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}