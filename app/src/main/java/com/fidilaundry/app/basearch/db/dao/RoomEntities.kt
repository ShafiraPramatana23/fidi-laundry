package com.fidilaundry.app.basearch.db.dao

import androidx.room.*
import com.fidilaundry.app.basearch.util.DBConstants

@Entity(tableName = DBConstants.USER_DATA_TABLE_NAME)
data class RoomUserDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val _id: String,
    @ColumnInfo(name = "full_name")
    val full_name: String,
    @ColumnInfo(name = "phone_number")
    val phone_number: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "flag_kyc")
    val flag_kyc: Boolean,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "rek_giropos")
    val rek_giropos: String? = null,
    @ColumnInfo(name = "rek_cimb_jgx")
    val rek_cimb_jgx: Long? = null,
    @ColumnInfo(name = "rek_cimb_kau")
    val rek_cimb_kau: Long? = null
)

