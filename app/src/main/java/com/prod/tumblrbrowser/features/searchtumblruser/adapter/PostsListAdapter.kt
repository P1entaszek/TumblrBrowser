package com.prod.tumblrbrowser.features.searchtumblruser.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prod.tumblrbrowser.R
import com.prod.tumblrbrowser.model.TumblrPost
import kotlinx.android.synthetic.main.recycler_view_post_model.view.*
import org.sufficientlysecure.htmltextview.HtmlTextView
import java.util.ArrayList


/**
 * Created by Piotr Jaszczurowski on 14.01.2020.
 */
class PostsListAdapter(private val posts: ArrayList<TumblrPost>, private val parentContext: Context) :
    RecyclerView.Adapter<PostsListAdapter.PostHolder>() {
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parentContext)
        val view = layoutInflater!!.inflate(R.layout.recycler_view_post_model, parent, false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val currentPost = posts[position]
        if (!currentPost.userPosted.isNullOrEmpty()) holder.userPosted.setHtml("Posted by: " + currentPost.userPosted)
        if (!currentPost.postTitle.isNullOrEmpty()) holder.title.setHtml(currentPost.postTitle)
        if (!currentPost.postBody.isNullOrEmpty()) holder.body.setHtml(currentPost.postBody)
        if (!currentPost.photoCaption.isNullOrEmpty()) holder.photoCaption.setHtml(currentPost.photoCaption)
        if (!currentPost.tags.isNullOrEmpty()) holder.tags.setHtml(currentPost.tags.toString())
        val photoUrl: String = getUrlWithHighestResolution(currentPost)
        Glide
            .with(parentContext)
            .load(photoUrl)
            .into(holder.avatar)

    }

    private fun getUrlWithHighestResolution(post: TumblrPost): String {
        if (!post.photoUrl1280.isNullOrEmpty()) return post.photoUrl1280
        if (!post.photoUrl500.isNullOrEmpty()) return post.photoUrl500
        if (!post.photoUrl400.isNullOrEmpty()) return post.photoUrl400
        if (!post.photoUrl250.isNullOrEmpty()) return post.photoUrl250
        else return ""
    }

    fun addNewPostsLists(postsList: ArrayList<TumblrPost>) {
        this.posts.addAll(postsList)
    }

    inner class PostHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val userPosted: HtmlTextView = v.userPosted
        val avatar: ImageView = v.avatar
        val photoCaption: HtmlTextView = v.photo_caption
        val title: HtmlTextView = v.title
        val body: HtmlTextView = v.body
        val tags: HtmlTextView = v.tags

        override fun onClick(v: View?) {
            Log.d("Clicked", "Recycler clicked")
        }

        init {
            v.setOnClickListener(this)
        }
    }
}