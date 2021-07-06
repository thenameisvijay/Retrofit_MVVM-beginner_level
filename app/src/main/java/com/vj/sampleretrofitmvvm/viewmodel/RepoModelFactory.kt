package com.vj.sampleretrofitmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vj.sampleretrofitmvvm.model.GitRepository

class RepoModelFactory constructor(private val gitRepository: GitRepository):ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(RepoViewModel::class.java))
            RepoViewModel(this.gitRepository) as T
        else
            throw IllegalArgumentException("View model not found")
    }
}