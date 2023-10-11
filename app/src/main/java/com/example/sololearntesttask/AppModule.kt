package com.example.sololearntesttask

import com.example.sololearntesttask.NetConstance.BASE_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
//    single { createRetrofit() }
    single { createWebService<GitHubApi>(BASE_URL) }
    single<GitHubRepository> { GitHubRepositoryImpl(get()) }
    viewModel { GitHubRepositoryViewModel(get()) }
}