package com.ace.converter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DBCurrency::class, DBCurrencyResult::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract val currencyDatabase: CurrencyDatabaseDao
    abstract val currencyDatabaseResult: CurrencyResultDatabaseDao

    companion object {

        @Volatile
        private var INSTANSE: CurrencyDatabase? = null

        fun getInstance(context: Context): CurrencyDatabase {
            synchronized(this) {
                var instance = INSTANSE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context?.applicationContext,
                            CurrencyDatabase::class.java,
                            "currency_database"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}