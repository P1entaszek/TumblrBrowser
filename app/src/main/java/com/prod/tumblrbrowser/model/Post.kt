package com.prod.tumblrbrowser.model

/**
 * Created by Piotr Jaszczurowski on 10.01.2020.
 */
data class Post(
    val id: String,
    val date_gmt: String,
    val photo_caption: String,
    val photo_url_1280: String,
    val tags: List<String>,
    val tumblelog: Tumblelog
)
