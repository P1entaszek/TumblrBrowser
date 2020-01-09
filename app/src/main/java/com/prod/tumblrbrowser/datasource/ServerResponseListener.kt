package com.prod.tumblrbrowser.datasource

import com.google.gson.JsonObject
import org.json.JSONObject

/**
 * Created by Piotr Jaszczurowski on 08.01.2020.
 */
interface ServerResponseListener<T> {
    fun onSuccess(response: JsonObject)
    fun onError(error: Throwable)
}