package com.prod.tumblrbrowser.model

/**
 * Created by Piotr Jaszczurowski on 10.01.2020.
 */
data class Post(
    val id: String,
    val dateGMT: String,
    val photoCaption: String,
    val photoUrl1280: String,
    val tags: List<String>,
    val tumblelog: Tumblelog
)
