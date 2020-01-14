package com.prod.tumblrbrowser.features.searchtumblruser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import com.prod.tumblrbrowser.R
import com.prod.tumblrbrowser.features.searchtumblruser.adapter.PostsListAdapter
import com.prod.tumblrbrowser.features.searchtumblruser.mvp.SearchTumblrUserMVP
import com.prod.tumblrbrowser.features.searchtumblruser.mvp.SearchTumblrUserPresenter
import com.prod.tumblrbrowser.model.TumblrPost
import com.prod.tumblrbrowser.model.UserAccount
import com.prod.tumblrbrowser.utils.Utils.hideKeyboard
import com.prod.tumblrbrowser.utils.Utils.setImageFromUrl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

/**
 * Created by Piotr Jaszczurowski on 07.01.2020
 */
class SearchTumblrUser : AppCompatActivity(), SearchTumblrUserMVP.View {
    lateinit var presenter: SearchTumblrUserPresenter
    private val compositeDisposable = CompositeDisposable()
    private val startingRequestPostLevel = 0
    val DOWNLOAD_DELAY_AFTER_TYPING_TEXT = 800L
    lateinit var postsListAdapter: PostsListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = SearchTumblrUserPresenter(this)
        presenter.initView()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun showError(message: String?) {
        Toast.makeText(
            this,
            this.getString(R.string.please_check_your_internet_connection),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showPosts(posts: List<TumblrPost>) {
        postsListAdapter = PostsListAdapter(posts, this)
        recycler_view.adapter = postsListAdapter
    }

    override fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
    }

    override fun showUserDetails(user: UserAccount) {
        userName.text = user.accountTitle
        userDescription.setHtml(user.accountDescription)
        setImageFromUrl(applicationContext, getUserImageUrl(user), userImgView)
        hideKeyboard(this, searchTumblrUserView)
        progressBar.visibility = View.INVISIBLE
    }

    private fun getUserImageUrl(user: UserAccount): String {
        val userImgUrl1: String
        if (!user.avatarUrl512.isEmpty()) {
            userImgUrl1 = user.avatarUrl512
            return userImgUrl1
        }
        if (!user.avatarUrl128.isEmpty()) {
            userImgUrl1 = user.avatarUrl128
            return userImgUrl1
        } else return ""
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
                        Log.d("rxview", "something is in")
                        progressBar.visibility = View.VISIBLE
                        presenter.searchTumblrUser(text, startingRequestPostLevel)
                    } else {
                        Log.d("rxview", "empty text")
                        progressBar.visibility = View.INVISIBLE
                        Toast.makeText(
                            this,
                            getString(R.string.please_type_some_user_to_search),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        )
    }

}
