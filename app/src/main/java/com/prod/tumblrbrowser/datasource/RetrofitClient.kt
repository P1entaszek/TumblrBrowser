package com.prod.tumblrbrowser.datasource

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Piotr Jaszczurowski on 08.01.2020.
 */
object RetrofitClient {
    private var instance: Retrofit? = null
    val retrofitInstance: Retrofit?
        get() {
            if (instance == null) {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val httpClient = OkHttpClient.Builder()
                    .addInterceptor { chain: Interceptor.Chain ->
                        var build = chain.request().newBuilder().build()
                        val request: Request = build
                        var response = chain.proceed(request)
                        val rawJson = response.body()!!.string()
                        val length = rawJson.length
                        val finalRawJson = rawJson.substring(22, length-2)
                        response = response.newBuilder()
                            .body(ResponseBody.create(response.body()!!.contentType(), finalRawJson))
                            .build()
                        response
                    }.build()
                instance = Retrofit.Builder().baseUrl("https://tumblr.com/api/read/json/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()
            }
            return instance
        }
}