package com.prod.tumblrbrowser.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Piotr Jaszczurowski on 10.01.2020.
 */
data class UserAccount(
    @SerializedName("name")
    val accountName: String,
    @SerializedName("description")
    val accountDescription: String,
    @SerializedName("title")
    val accountTitle: String,
    var avatarUrl512: String,
    var avatarUrl128: String
)