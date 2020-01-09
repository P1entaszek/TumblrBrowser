package com.prod.tumblrbrowser.datasource

import com.google.gson.JsonObject
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by Piotr Jaszczurowski on 08.01.2020.
 */
interface ApiService {

    @GET
    fun getResponse(@Url url: String): Observable<JsonObject?>?
}