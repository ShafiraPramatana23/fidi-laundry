package com.fidilaundry.app.basearch.di.module

import com.fidilaundry.app.basearch.repository.*
import org.koin.dsl.module

val reposModule = module {

    single<LoginRepository> {
        LoginRepositoryImpl(
            api = get(),
            paperPrefs = get()
        )
    }
}
