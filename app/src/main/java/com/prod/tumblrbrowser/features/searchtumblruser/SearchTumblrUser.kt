package com.prod.tumblrbrowser.features.searchtumblruser

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.widget.RxTextView
import com.prod.tumblrbrowser.R
import com.prod.tumblrbrowser.features.searchtumblruser.adapter.PostsListAdapter
import com.prod.tumblrbrowser.features.searchtumblruser.mvp.SearchTumblrUserMVP
import com.prod.tumblrbrowser.features.searchtumblruser.mvp.SearchTumblrUserPresenter
import com.prod.tumblrbrowser.model.TumblrPost
import com.prod.tumblrbrowser.model.UserAccount
import com.prod.tumblrbrowser.utils.Utils
import com.prod.tumblrbrowser.utils.Utils.hideKeyboard
import com.prod.tumblrbrowser.utils.Utils.setImageFromUrl
import com.prod.tumblrbrowser.utils.Utils.showToast
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
    private var postsList = ArrayList<TumblrPost>()
    private var userQuery = ""
    private val compositeDisposable = CompositeDisposable()
    private var postStart = 0
    val DOWNLOAD_DELAY_AFTER_TYPING_TEXT = 800L
    lateinit var postsListAdapter: PostsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = SearchTumblrUserPresenter(this)
        presenter.initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun showError(error: Throwable) {
        if (!Utils.isInternetConnectionAvailable(this)) {
            showToast(this, getString(R.string.error_please_check_your_internet_connection))
            userDescription.text =  getString(R.string.error_please_check_your_internet_connection)
        }
        else {
            showToast(this, getString(R.string.error_no_user_found))
        }
    }
    
    override fun showPosts(posts: ArrayList<TumblrPost>, postStart: Int) {
        postsListAdapter.addNewPostsLists(posts)
        recycler_view.scrollToPosition(postStart)
        postsListAdapter.notifyItemInserted(postStart)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun hideStartingScreen() {
        userPostsLayout.visibility = View.VISIBLE
        startingTextView.visibility = View.INVISIBLE
    }

    override fun hideKeyboard() {
        hideKeyboard(this, searchTumblrUserView)
    }

    override fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        postsListAdapter = PostsListAdapter(postsList, this)
        recycler_view.adapter = postsListAdapter
        addScrollListenerToRecyclerView()
    }

    private fun addScrollListenerToRecyclerView() {
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager =
                    LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                if(layoutManager!=null){
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val endHasBeenReached = lastVisible + 1 >= totalItemCount
                    if (totalItemCount > 0 && endHasBeenReached) { //y
                        presenter.loadMorePosts(userQuery)
                    }
                }
            }
        })
    }

    override fun showUserDetails(user: UserAccount) {
        userName.text = user.accountTitle
        userDescription.setHtml(user.accountDescription)
        setImageFromUrl(applicationContext, getUserImageUrl(user), userImgView)
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
                        userQuery = text
                        postsList.clear()
                        postStart = 0
                        clearLayout()
                        presenter.searchTumblrUser(text, postStart)

                    } else {
                        showToast(this, getString(R.string.please_type_some_user_to_search))
                    }
                }
        )
    }

    private fun clearLayout() {
        recycler_view.recycledViewPool.clear()
        postsListAdapter.notifyDataSetChanged()
        userDescription.text =  getString(R.string.no_user_found)
        userName.text = ""
        Glide
            .with(this)
            .load(getDrawable(R.drawable.ic_launcher_background))
            .centerCrop()
            .into(userImgView)
    }

}
