package com.ace.converter.network

import com.ace.converter.model.Currencies
import com.ace.converter.model.ModelResponse
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
    ): Single<ModelResponse>
}