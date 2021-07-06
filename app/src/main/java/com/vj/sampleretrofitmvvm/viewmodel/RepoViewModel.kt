package com.vj.sampleretrofitmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vj.sampleretrofitmvvm.model.GitRepository
import com.vj.sampleretrofitmvvm.network.Resource
import kotlinx.coroutines.Dispatchers

class RepoViewModel constructor(private val gitRepository: GitRepository): ViewModel() {

    fun getRepoList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = gitRepository.callUserData()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}