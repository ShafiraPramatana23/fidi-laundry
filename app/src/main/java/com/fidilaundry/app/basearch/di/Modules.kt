@file:Suppress("RemoveExplicitTypeArguments")

package com.fidilaundry.app.basearch.di
import com.fidilaundry.app.basearch.di.module.*

val modulesList = listOf(
    networkingModule,
    dbModule,
    viewModelsModule,
    reposModule,
    workersModule
)
