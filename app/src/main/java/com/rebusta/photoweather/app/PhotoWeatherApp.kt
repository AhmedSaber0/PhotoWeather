package com.rebusta.photoweather.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.rebusta.photoweather.di.*
import com.rebusta.photoweather.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PhotoWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PhotoWeatherApp)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    dataSourceModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}