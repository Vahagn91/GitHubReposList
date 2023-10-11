package com.example.sololearntesttask

import android.app.Application
import androidx.fragment.app.Fragment
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class GitRepoApp : Application() {

        override fun onCreate() {
            super.onCreate()

            startKoin{
                androidLogger(Level.ERROR)
                androidContext(this@GitRepoApp)
                modules(appModule)
            }
        }


    companion object {
        var mCurrentFragment: Fragment? = null

    }
}