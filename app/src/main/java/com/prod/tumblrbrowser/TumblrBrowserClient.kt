package com.prod.tumblrbrowser

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.prod.tumblrbrowser.datasource.ApiService
import com.prod.tumblrbrowser.datasource.RetrofitClient
import com.prod.tumblrbrowser.datasource.ServerResponseListener
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Piotr Jaszczurowski on 08.01.2020.
 */
class TumblrBrowserClient {

    private val apiService: ApiService?
        get() {
            val retrofit = RetrofitClient.retrofitInstance
            return retrofit?.create(ApiService::class.java)
        }

    fun getUserPosts(userName: String, postsStart: Int, listener: ServerResponseListener<JsonObject?>){
        val finalUrl = "https://$userName.tumblr.com/api/read/json?start=$postsStart"
        apiService!!.getResponse(finalUrl)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<JsonObject?>{
                override fun onComplete() {
                    //no-op
                }

                override fun onSubscribe(d: Disposable) {
                    //no-op
                }

                override fun onNext(json: JsonObject) {
                    listener.onSuccess(json)
                }

                override fun onError(e: Throwable) {
                    listener.onError(e)
                }
            })
    }

    companion object {
        val tumblrBrowserClient: TumblrBrowserClient = TumblrBrowserClient()
    }
}