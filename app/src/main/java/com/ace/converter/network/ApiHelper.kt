package com.ace.converter.network

import com.ace.converter.entity.Currencies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHelper {

    @GET("api/v7/currencies")
    fun getCurrencies(): Single<Currencies>

    @GET("/api/v7/convert")
    fun getValueCurrencies(
            @Query("q") currencies: String,
            @Query("compact") compact: String
    ): Single<Map<String, Float>>
}