package com.vj.sampleretrofitmvvm.model

import com.vj.sampleretrofitmvvm.network.GithubEndpoint

class GitRepository constructor(private val endpoint: GithubEndpoint) {
    suspend fun callUserData() = endpoint.requestUserData()
}