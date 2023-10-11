package com.example.sololearntesttask.di

import com.example.sololearntesttask.network.NetConstants.BASE_URL
import com.example.sololearntesttask.main.GitHubRepositoryViewModel
import com.example.sololearntesttask.network.GitHubApi
import com.example.sololearntesttask.network.GitHubRepository
import com.example.sololearntesttask.network.GitHubRepositoryImpl
import com.example.sololearntesttask.network.GitReposInteractor
import com.example.sololearntesttask.network.createWebService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { createWebService<GitHubApi>(BASE_URL) }
    factory <GitHubRepository> { GitHubRepositoryImpl(get()) }
    factory { GitReposInteractor(get()) }
    viewModel { GitHubRepositoryViewModel(get()) }
}