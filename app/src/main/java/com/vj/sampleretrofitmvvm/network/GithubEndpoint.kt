package com.vj.sampleretrofitmvvm.network

import com.vj.sampleretrofitmvvm.model.RepoResponse
import retrofit2.http.GET

interface GithubEndpoint {
    @GET("users")
    suspend fun requestUserData(): ArrayList<RepoResponse>

}