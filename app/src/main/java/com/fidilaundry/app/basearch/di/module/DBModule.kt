@file:Suppress("RemoveExplicitTypeArguments")

package com.fidilaundry.app.basearch.di.module

import com.fidilaundry.app.basearch.db.PosgoDB
import com.fidilaundry.app.basearch.db.dao.*
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {

    single { PaperPrefs(androidApplication()) }

    single<PosgoDB> {
        PosgoDB.create(androidContext())
    }

    factory<GoldBalanceDao> {
        get<PosgoDB>().golBalanceDao()
    }
}

