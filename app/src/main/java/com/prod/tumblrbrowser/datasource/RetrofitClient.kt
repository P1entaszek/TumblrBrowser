package com.prod.tumblrbrowser.datasource

import com.google.gson.GsonBuilder
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Piotr Jaszczurowski on 08.01.2020.
 */
object RetrofitClient {
    private val serverBaseUrl = "https://tumblr.com/api/read/json/"
    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()
    val retrofitInstance: Retrofit?
        get() {
            if (instance == null) {
                val httpClient = OkHttpClient.Builder()
                    .addInterceptor { chain: Interceptor.Chain ->
                        getProperJsonResponse(chain)
                    }.build()
                instance = Retrofit.Builder().baseUrl(serverBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()
            }
            return instance
        }

    private fun getProperJsonResponse(chain: Interceptor.Chain): Response {
        val build = chain.request().newBuilder().build()
        val request: Request = build
        var response = chain.proceed(request)
        val rawJson = response.body()!!.string()
        val length = rawJson.length
        val finalRawJson = rawJson.substring(22, length - 2)
        response = response.newBuilder()
            .body(ResponseBody.create(response.body()!!.contentType(), finalRawJson))
            .build()
        return response
    }
}