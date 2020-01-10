package com.prod.tumblrbrowser.model

/**
 * Created by Piotr Jaszczurowski on 10.01.2020.
 */
data class UserAccount(
    val accountName: String,
    val accountDescription: String,
    val accountTitle: String,
    val avatar_url_512: String
)