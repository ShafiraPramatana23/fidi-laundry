package com.fidilaundry.app

object MainApplicationContract {
    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    const val DEFAULT_UI_DELAY: Long = 300L
    const val START_DELAY_MILLIS: Long = 5000L

    const val DATABASE_NAME = BuildConfig.DB_NAME

    const val API_BASE_URL = BuildConfig.API_BASE_URL
    const val API_GTM = BuildConfig.API_GTM
    const val API_DISTRICT = BuildConfig.API_DISTRICT
    const val API_GEO = BuildConfig.API_GEO


    const val DEFAULT_NETWORK_PAGE_SIZE = 10

    const val NETWORK_TAG = "LOG_Network"
}
