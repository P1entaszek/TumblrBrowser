package com.prod.tumblrbrowser.features.searchtumblruser.mvp

import com.prod.tumblrbrowser.model.TumblrPost
import com.prod.tumblrbrowser.model.UserAccount

/**
 * Created by Piotr Jaszczurowski on 13.01.2020.
 */
interface SearchTumblrUserMVP {
    interface View {
        fun showError()
        fun setupRecyclerView()
        fun showPosts()
        fun showUserDetails()
        fun setupSearchingBar()
    }

    interface Presenter {
        fun initView()
        fun validateUserSearchingQuery(string: String)
        fun gotTumblrPosts()
    }

    interface Interactor {
        fun getTumblrPosts(
            searchedTumblrUser: String,
            postStart: Int,
            sercallback: GetTumblrPostsCallback
        )

        interface GetTumblrPostsCallback {
            fun onGetTumblrPostsSuccessCallback(user: UserAccount?, tumblrPosts: List<TumblrPost>)
            fun onGetTumblrPostsErrorCallback(error: Throwable)
        }
    }
}