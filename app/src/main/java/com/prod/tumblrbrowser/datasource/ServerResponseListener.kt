package com.prod.tumblrbrowser.datasource

import com.prod.tumblrbrowser.model.TumblrPost
import com.prod.tumblrbrowser.model.UserAccount

/**
 * Created by Piotr Jaszczurowski on 08.01.2020.
 */
interface ServerResponseListener<T, U> {
    fun onSuccess(user: UserAccount, tumblrPosts: List<TumblrPost>)
    fun onError(error: Throwable)
}