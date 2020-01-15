package com.prod.tumblrbrowser.features.searchtumblruser.mvp

import com.prod.tumblrbrowser.model.TumblrPost
import com.prod.tumblrbrowser.model.UserAccount

/**
 * Created by Piotr Jaszczurowski on 13.01.2020.
 */
interface SearchTumblrUserMVP {

    interface View {
        fun setupRecyclerView()
        fun setupSearchingBar()
        fun showUserDetails(user: UserAccount)
        fun showError(message: String?)
        fun showPosts(posts: List<TumblrPost>, postStart: Int)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter : Interactor.GetTumblrPostsCallback {
        fun initView()
        fun validateUserSearchingQuery(query: String)
        fun searchTumblrUser(query: String, startingRequestPostLevel: Int)
        fun loadMorePosts(userQuery: String)
    }

    interface Interactor {
        fun getTumblrPosts(
            searchedTumblrUser: String,
            postStart: Int,
            serverCallback: GetTumblrPostsCallback
        )

        interface GetTumblrPostsCallback {
            fun onGetTumblrPostsSuccessCallback(user: UserAccount, tumblrPosts: List<TumblrPost>)
            fun onGetTumblrPostsErrorCallback(error: Throwable)
        }
    }
}