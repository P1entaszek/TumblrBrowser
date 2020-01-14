package com.prod.tumblrbrowser.features.searchtumblruser.mvp

import com.prod.tumblrbrowser.model.TumblrPost
import com.prod.tumblrbrowser.model.UserAccount

/**
 * Created by Piotr Jaszczurowski on 13.01.2020.
 */
class SearchTumblrUserPresenter(
    val view: SearchTumblrUserMVP.View,
    val interactor: SearchTumblrUserInteractor = SearchTumblrUserInteractor()
) : SearchTumblrUserMVP.Presenter {

    override fun initView() {
        view.setupRecyclerView()
        view.setupSearchingBar()
    }

    override fun validateUserSearchingQuery(query: String) {

    }

    override fun searchTumblrUser(query: String, startingRequestPostLevel: Int) {
        interactor.getTumblrPosts(query, startingRequestPostLevel, this)
    }

    override fun onGetTumblrPostsSuccessCallback(user: UserAccount, tumblrPosts: List<TumblrPost>) {
        view.showUserDetails(user)
        view.showPosts(tumblrPosts)
    }

    override fun onGetTumblrPostsErrorCallback(error: Throwable) {
        view.showError(error.localizedMessage)
    }

}