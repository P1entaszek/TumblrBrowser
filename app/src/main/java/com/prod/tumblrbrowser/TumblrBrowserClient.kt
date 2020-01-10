package com.prod.tumblrbrowser

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.prod.tumblrbrowser.datasource.ApiService
import com.prod.tumblrbrowser.datasource.RetrofitClient
import com.prod.tumblrbrowser.datasource.ServerResponseListener
import com.prod.tumblrbrowser.model.Post
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
                    val postsList = ArrayList<Post>()
                    val accountName = userName
                    val account = json.getAsJsonObject("tumblelog")
                    val jsonPosts = json.get("posts").asJsonArray
                    val accountAvatarPhotoUrl = jsonPosts.get(0).asJsonObject.get("tumblelog").asJsonObject.get("avatar_url_512").asString
                    val userAccount = UserAccount(
                        userName,
                        account.asJsonObject.get("description").asString,
                        account.asJsonObject.get("title").asString,
                        accountAvatarPhotoUrl
                    )
                    jsonPosts.forEach {
                        val photoUrl = it.asJsonObject.get("photo-url-1280").asString
                        val tumblelog = it.asJsonObject.get("tumblelog")
                        val tumblelogModel = Tumblelog(accountName,
                            accountAvatarPhotoUrl,
                            tumblelog.asJsonObject.get("title").asString,
                            photoUrl)

                        val post = Post(
                            it.asJsonObject.get("id").asString,
                            it.asJsonObject.get("date-gmt").asString,
                            it.asJsonObject.get("photo-caption").asString,
                            photoUrl,
                            getTagsLists(it.asJsonObject.get("tags").asJsonArray),
                            tumblelogModel
                        )
                        postsList.add(post)
                    }

                    listener.onSuccess(userAccount, postsList)
                }

                override fun onError(e: Throwable) {
                    listener.onError(e)
                }
            })
    }

    private fun getTagsLists(jsonArray: JsonArray): List<String>{
        val list: MutableList<String> = ArrayList()
        for (i in 0 until jsonArray.size()) {
            list.add(jsonArray.get(i).asString)
        }
        return list
    }

    companion object {
        val tumblrBrowserClient: TumblrBrowserClient = TumblrBrowserClient()
    }
}