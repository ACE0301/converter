package com.ace.converter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Single

@Dao
interface CurrencyDatabaseDao {
    @Insert
    fun insert(currencyPair: List<DBCurrency>)

    @Update
    fun update(currencyPair: List<DBCurrency>)

    @Query("SELECT * FROM currency_table")
    fun getAllCurrencies(): Single<List<DBCurrency>>

    @Query("DELETE FROM currency_table")
    fun clear()

}

@Dao
interface CurrencyResultDatabaseDao {
    @Insert
    fun insert(result: DBCurrencyResult)

    @Update
    fun update(result: DBCurrencyResult)

    @Query("SELECT * FROM currency_result_table WHERE pair = :key")
    fun get(key: String): Single<DBCurrencyResult>

    @Query("DELETE FROM currency_result_table")
    fun clear()

}

