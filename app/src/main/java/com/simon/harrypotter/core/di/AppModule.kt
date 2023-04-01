package com.simon.harrypotter.core.di

import com.simon.harrypotter.core.viewModels.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel(get()) }
}