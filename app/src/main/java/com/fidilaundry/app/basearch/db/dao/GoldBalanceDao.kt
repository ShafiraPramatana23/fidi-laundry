package com.fidilaundry.app.basearch.db.dao

import androidx.room.Dao

@Dao
interface GoldBalanceDao {

    /* Add right query here, filter on dates*/
    /*@Query("SELECT * FROM ${DBConstants.GOLD_BALANCE_TABLE_NAME}")
    fun getGoldBalance(): Flow<List<RoomGoldBalanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveGoldBalance(goldBalance: RoomGoldBalanceEntity)*/
}
