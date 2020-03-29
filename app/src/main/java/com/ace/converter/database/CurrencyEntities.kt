package com.ace.converter.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class DBCurrency(
        @PrimaryKey
        @ColumnInfo(name = "currency")
        val currency: String = ""
)

@Entity(tableName = "currency_result_table")
data class DBCurrencyResult(

        @PrimaryKey
        @ColumnInfo(name = "pair")
        val pair: String = "",

        @ColumnInfo(name = "result")
        val result: Float = 0.0f
)