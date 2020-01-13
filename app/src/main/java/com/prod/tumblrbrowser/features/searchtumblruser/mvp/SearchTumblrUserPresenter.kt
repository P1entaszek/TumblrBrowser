package com.prod.tumblrbrowser.features.searchtumblruser.mvp

/**
 * Created by Piotr Jaszczurowski on 13.01.2020.
 */
class SearchTumblrUserPresenter(val view: SearchTumblrUserMVP.View, val interactor: SearchTumblrUserInteractor = SearchTumblrUserInteractor()) : SearchTumblrUserMVP.Presenter {

    override fun initView() {
        view.setupRecyclerView()
        view.setupSearchingBar()
    }

    override fun validateUserSearchingQuery(query: String) {

    }

    override fun gotTumblrPosts() {

    }

    fun searchTumblrUser(query: String) {

    }

    fun showError() {

    }
}