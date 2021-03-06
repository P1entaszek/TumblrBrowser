package com.prod.tumblrbrowser.features.searchtumblruser.mvp

import com.prod.tumblrbrowser.model.TumblrPost
import com.prod.tumblrbrowser.model.UserAccount

/**
 * Created by Piotr Jaszczurowski on 13.01.2020.
 */
class SearchTumblrUserPresenter(
    val view: SearchTumblrUserMVP.View,
    private val interactor: SearchTumblrUserInteractor = SearchTumblrUserInteractor()
) : SearchTumblrUserMVP.Presenter {
    private var postStart = 0

    override fun initView() {
        view.setupRecyclerView()
        view.setupSearchingBar()
    }

    override fun searchTumblrUser(query: String, postStart: Int) {
        view.showProgressBar()
        this.postStart = postStart
        interactor.getTumblrPosts(query, postStart, this)
    }

    override fun loadMorePosts(userQuery: String) {
        postStart += 20
        view.showProgressBar()
        interactor.getTumblrPosts(userQuery, postStart, this)
    }

    override fun onGetTumblrPostsSuccessCallback(user: UserAccount, tumblrPosts: ArrayList<TumblrPost>) {
        view.showUserDetails(user)
        view.showPosts(tumblrPosts, postStart)
        view.hideProgressBar()
        view.hideStartingScreen()
        view.hideKeyboard()
    }

    override fun onGetTumblrPostsErrorCallback(error: Throwable) {
        view.showError(error)
        view.hideProgressBar()
        view.hideKeyboard()
    }
}