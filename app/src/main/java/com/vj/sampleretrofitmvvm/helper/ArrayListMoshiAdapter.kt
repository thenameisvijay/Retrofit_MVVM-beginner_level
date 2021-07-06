package com.vj.sampleretrofitmvvm.helper

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.vj.sampleretrofitmvvm.model.RepoResponse

//No need to use this class if we are using Gson convertor
class ArrayListMoshiAdapter {
    @ToJson
    fun arrayListToJson(list: ArrayList<RepoResponse>): List<RepoResponse> = list

    @FromJson
    fun arrayListFromJson(list: List<RepoResponse>): ArrayList<RepoResponse> = ArrayList(list)
}