package com.prod.tumblrbrowser.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Piotr Jaszczurowski on 10.01.2020.
 */
data class TumblrPost(
    @SerializedName("id")
    val id: String,
    @SerializedName("date-gmt")
    val dateGMT: String,
    @SerializedName("reblogged-root-name")
    val userOriginalPosted: String,
    @SerializedName("reblogged-from-name")
    val userPosted: String,
    @SerializedName("regular-title")
    val postTitle: String,
    @SerializedName("regular-body")
    val postBody: String,
    @SerializedName("photo-caption")
    val photoCaption: String,
    @SerializedName("photo-url-1280")
    var photoUrl1280: String,
    @SerializedName("photo-url-500")
    var photoUrl500: String,
    @SerializedName("photo-url-400")
    var photoUrl400: String,
    @SerializedName("photo-url-250")
    var photoUrl250: String,
    @SerializedName("tags")
    val tags: List<String>,
    var tumblelog: Tumblelog
)
