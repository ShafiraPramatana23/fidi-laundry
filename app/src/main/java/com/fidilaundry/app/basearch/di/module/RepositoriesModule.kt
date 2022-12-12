package com.fidilaundry.app.basearch.di.module

import com.fidilaundry.app.basearch.repository.*
import org.koin.dsl.module

val reposModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(
            api = get(),
            paperPrefs = get()
        )
    }
}
