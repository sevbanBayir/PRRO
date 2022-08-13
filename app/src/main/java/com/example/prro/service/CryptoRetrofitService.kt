package com.example.prro.service

import com.example.prro.model.CoinListModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CryptoRetrofitService {
    private val BASE_URL = "https://rest.coinapi.io/"

    /* private val api = Retrofit.Builder()
         .baseUrl(BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
         .build()
         .create(BesinAPI::class.java)

     fun getData(): Single<List<BesinListesiModel>> {
         return api.getBesin()

     */
    val api =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(CryptoAPI::class.java)

 fun getCoins(): Single <List<CoinListModel>> {
     return api.getDataAPI()
 }

}