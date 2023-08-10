@file:Suppress("RemoveExplicitTypeArguments")

package com.fidilaundry.app.basearch.di.module
import com.fidilaundry.app.basearch.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { LoginViewModel(authRepository = get(), profileRepository = get()) }
    viewModel { RegisterViewModel(authRepository = get()) }
    viewModel { HomeViewModel(authRepository = get(), orderRepository = get()) }
    viewModel { ProfileViewModel(authRepository = get()) }
    viewModel { OrderViewModel(orderRepository = get()) }
    viewModel { MasterViewModel(masterRepository = get()) }
    viewModel { CustomerViewModel(customerRepository = get()) }
    viewModel { ComplaintViewModel(complaintRepository = get()) }
    viewModel { HistoryViewModel(historyRepository = get()) }
    viewModel { ScannerViewModel(customerRepository = get(), orderRepository = get()) }
}
