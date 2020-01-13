package com.prod.tumblrbrowser.features.searchtumblruser.mvp

import com.prod.tumblrbrowser.TumblrBrowserClient
import com.prod.tumblrbrowser.datasource.ServerResponseListener
import com.prod.tumblrbrowser.model.TumblrPost
import com.prod.tumblrbrowser.model.UserAccount

/**
 * Created by Piotr Jaszczurowski on 13.01.2020.
 */
class SearchTumblrUserInteractor : SearchTumblrUserMVP.Interactor {
    override fun getTumblrPosts(
        searchedTumblrUser: String,
        postStart: Int,
        sercallback: SearchTumblrUserMVP.Interactor.GetTumblrPostsCallback
    ) {
        TumblrBrowserClient.tumblrBrowserClient.getUserPosts(searchedTumblrUser, postStart, object :
            ServerResponseListener<UserAccount, List<TumblrPost>> {
            override fun onSuccess(user: UserAccount, tumblrPosts: List<TumblrPost>) {
                sercallback.onGetTumblrPostsSuccessCallback(user, tumblrPosts)
            }

            override fun onError(error: Throwable) {
                sercallback.onGetTumblrPostsErrorCallback(error)
            }
        })


    }
}