package com.prod.tumblrbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.JsonObject
import com.prod.tumblrbrowser.datasource.ServerResponseListener

/**
 * Created by Piotr Jaszczurowski on 07.01.2020
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TumblrBrowserClient.tumblrBrowserClient.getUserPosts("ksiezniczka-z-porcelany", 20, object:
            ServerResponseListener<JsonObject?> {
            override fun onSuccess(response: JsonObject) {
            Toast.makeText(applicationContext, "udalo sie", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: Throwable) {
            Toast.makeText(applicationContext, "szlag!", Toast.LENGTH_LONG).show()
            }
        })
    }

}
