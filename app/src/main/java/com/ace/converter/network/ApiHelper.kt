package com.ace.converter.network

import com.ace.converter.model.ModelResponce
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiHelper {

//https://free.currconv.com/api/v7/convert?q=EUR_RUB&compact=ultra&apiKey=0df0077d2f038ffb3510

    @GET("/api/v7/convert?&compact=ultra&apiKey=0df0077d2f038ffb3510")
    fun getAbrCurrencies(
        @Query("q") currencies: String
    ): Single<ModelResponce>


}