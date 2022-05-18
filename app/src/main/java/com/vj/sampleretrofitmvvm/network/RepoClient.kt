package com.vj.sampleretrofitmvvm.network

import androidx.viewbinding.BuildConfig
import com.squareup.moshi.Moshi
import com.vj.sampleretrofitmvvm.BuildConfig.BASE_URL
import com.vj.sampleretrofitmvvm.helper.ArrayListMoshiAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RepoClient {

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BODY
        })
        .build()

    fun create(): GithubEndpoint =
        Retrofit.Builder().run {
            addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi
                        .Builder()
                        .add(ArrayListMoshiAdapter())
                        .build()
                )
            )
            client(client)
            baseUrl(BASE_URL)
            build()
        }.create(GithubEndpoint::class.java)
}