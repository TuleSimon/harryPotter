package com.simon.harrypotter.core.app

import android.app.Application
import com.simon.data.di.LocalModule
import com.simon.data.di.NetworkModule
import com.simon.harrypotter.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class SimonApp():Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@SimonApp)
            modules(NetworkModule, LocalModule,appModule)
        }
        Timber.plant(Timber.DebugTree())
    }
}