@file:Suppress("RemoveExplicitTypeArguments")

package com.fidilaundry.app.basearch.di.module
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.basearch.viewmodel.LoginViewModel
import com.fidilaundry.app.basearch.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { LoginViewModel(authRepository = get()) }
    viewModel { RegisterViewModel(authRepository = get()) }
    viewModel { HomeViewModel(authRepository = get()) }
}
