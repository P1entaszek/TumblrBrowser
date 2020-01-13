package com.prod.tumblrbrowser.features.searchtumblruser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.JsonObject
import com.jakewharton.rxbinding2.widget.RxTextView
import com.prod.tumblrbrowser.R
import com.prod.tumblrbrowser.TumblrBrowserClient
import com.prod.tumblrbrowser.datasource.ServerResponseListener
import com.prod.tumblrbrowser.features.searchtumblruser.mvp.SearchTumblrUserInteractor
import com.prod.tumblrbrowser.features.searchtumblruser.mvp.SearchTumblrUserMVP
import com.prod.tumblrbrowser.features.searchtumblruser.mvp.SearchTumblrUserPresenter
import com.prod.tumblrbrowser.model.TumblrPost
import com.prod.tumblrbrowser.model.UserAccount
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

/**
 * Created by Piotr Jaszczurowski on 07.01.2020
 */
class SearchTumblrUser : AppCompatActivity(), SearchTumblrUserMVP.View {
    lateinit var presenter:SearchTumblrUserPresenter
    val compositeDisposable = CompositeDisposable()
    val DOWNLOAD_DELAY_AFTER_TYPING_TEXT = 800L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = SearchTumblrUserPresenter(this)
        presenter.initView()
        TumblrBrowserClient.tumblrBrowserClient.getUserPosts("czlowiektoniezabawka", 0, object:
            ServerResponseListener<UserAccount, List<TumblrPost>> {
            override fun onSuccess(user:UserAccount, tumblrPosts:  List<TumblrPost>) {
                Toast.makeText(applicationContext, "i got it", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: Throwable) {
            Toast.makeText(applicationContext, "damn!", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun showError() {

    }

    override fun setupRecyclerView() {

    }

    override fun showPosts() {

    }

    override fun showUserDetails() {

    }

    override fun setupSearchingBar() {
        compositeDisposable.add(
            RxTextView.afterTextChangeEvents(userSearchingBar)
                .debounce(DOWNLOAD_DELAY_AFTER_TYPING_TEXT, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skip(1)
                .map { it.view().text.toString() }
                .subscribe { text ->
                    if (text.isNotEmpty()) {
                        presenter.searchTumblrUser(text)
                    } else {
                        presenter.showError()
                    }
                }
        )
    }

}
