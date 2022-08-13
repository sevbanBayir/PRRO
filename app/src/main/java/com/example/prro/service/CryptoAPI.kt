package com.example.prro.service

import com.example.prro.model.CoinListModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    //GET, POST, UPDATE, DELETE
    //https://rest.coinapi.io/v1/assets?apikey=C812DD47-3C54-4689-8917-EF4B3CE37468
    @GET("v1/assets?apikey=C812DD47-3C54-4689-8917-EF4B3CE37468")
    fun getDataAPI () : Single <List<CoinListModel>>
}