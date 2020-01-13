package com.prod.tumblrbrowser.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Piotr Jaszczurowski on 10.01.2020.
 */
data class Tumblelog(
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar_url_512")
    val avatarUrl512: String,
    @SerializedName("avatar_url_128")
    val avatarUrl128: String,
    @SerializedName("title")
    val title: String
)