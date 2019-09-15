package com.ace.converter.network

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val BASE_URL = "https://free.currconv.com"

object ApiHolder {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ApiHelper::class.java)
}

