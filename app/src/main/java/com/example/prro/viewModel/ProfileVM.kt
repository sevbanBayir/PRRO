package com.example.prro.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prro.model.CoinListModel
import com.example.prro.service.CryptoAPI
import com.example.prro.service.CryptoRetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileVM (application: Application): AndroidViewModel (application) {

    private val context = getApplication<Application>().applicationContext
    var coins = MutableLiveData<List<CoinListModel>>()
    var progressBar= MutableLiveData<Boolean>()

    private var coinAPIService = CryptoRetrofitService()
    private var disposable = CompositeDisposable()

    fun fromInternet() {
        progressBar.value=true

        disposable.add(
            coinAPIService.getCoins()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CoinListModel>>() {
                    override fun onSuccess(t: List<CoinListModel>) {
                        coins.value=t
                        progressBar.value= false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        Toast.makeText(context,"Veriler İndirilemedi", Toast.LENGTH_SHORT).show()
                        progressBar.value= false
                    }
                }
            )
        )
    }
}