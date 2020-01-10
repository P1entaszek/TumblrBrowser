package com.prod.tumblrbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.JsonObject
import com.prod.tumblrbrowser.datasource.ServerResponseListener
import com.prod.tumblrbrowser.model.Post
import com.prod.tumblrbrowser.model.UserAccount

/**
 * Created by Piotr Jaszczurowski on 07.01.2020
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TumblrBrowserClient.tumblrBrowserClient.getUserPosts("flaroh", 0, object:
            ServerResponseListener<JsonObject?> {
            override fun onSuccess(user:UserAccount, posts:  List<Post>) {
                Toast.makeText(applicationContext, "i got it", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: Throwable) {
            Toast.makeText(applicationContext, "damn!", Toast.LENGTH_LONG).show()
            }
        })
    }

}
