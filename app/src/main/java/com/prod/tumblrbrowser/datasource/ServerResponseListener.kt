package com.prod.tumblrbrowser.datasource

import com.google.gson.JsonObject
import com.prod.tumblrbrowser.model.Post
import com.prod.tumblrbrowser.model.UserAccount
import org.json.JSONObject

/**
 * Created by Piotr Jaszczurowski on 08.01.2020.
 */
interface ServerResponseListener<T> {
    fun onSuccess(user: UserAccount, posts: List<Post>)
    fun onError(error: Throwable)
}