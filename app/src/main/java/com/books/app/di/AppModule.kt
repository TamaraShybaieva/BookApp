package com.books.app.di

import DetailsViewModel
import com.books.app.presentation.ui.fragment.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    val appModule = module {

        viewModel {
            MainViewModel()
        }
        viewModel {
            DetailsViewModel()
        }
    }
}