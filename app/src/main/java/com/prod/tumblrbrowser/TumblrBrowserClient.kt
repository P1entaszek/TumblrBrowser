package com.prod.tumblrbrowser

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.prod.tumblrbrowser.datasource.ApiService
import com.prod.tumblrbrowser.datasource.RetrofitClient
import com.prod.tumblrbrowser.datasource.ServerResponseListener
import com.prod.tumblrbrowser.model.TumblrPost
import com.prod.tumblrbrowser.model.Tumblelog
import com.prod.tumblrbrowser.model.UserAccount
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

    fun getUserPosts(userName: String, postsStart: Int, listener: ServerResponseListener<UserAccount, List<TumblrPost>>){
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
                    val gson = GsonBuilder().serializeNulls().create()
                    val postsList = ArrayList<TumblrPost>()
                    val account = json.getAsJsonObject("tumblelog")
                    val jsonPosts = json.get("posts").asJsonArray
                    val userAccount = gson.fromJson(account, UserAccount::class.java)
                    jsonPosts.forEach {
                        val tumblelog = it.asJsonObject.get("tumblelog")
                        val tumblelogModel = gson.fromJson(tumblelog, Tumblelog::class.java)
                        val post = gson.fromJson(it, TumblrPost::class.java)
                        post.tumblelog = tumblelogModel
                        postsList.add(post)
                    }
                    userAccount.avatarUrl512 = postsList.get(0).tumblelog.avatarUrl512
                    userAccount.avatarUrl128 = postsList.get(0).tumblelog.avatarUrl128
                    listener.onSuccess(userAccount, postsList)
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