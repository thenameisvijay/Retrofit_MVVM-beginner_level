package com.vj.sampleretrofitmvvm.viewmodels

import androidx.test.platform.app.InstrumentationRegistry
import com.vj.sampleretrofitmvvm.AndroidTestUtils
import com.vj.sampleretrofitmvvm.GithubEndpoint
import com.vj.sampleretrofitmvvm.NetworkBuilderMock
import com.vj.sampleretrofitmvvm.model.GitRepository
import com.vj.sampleretrofitmvvm.network.RepoClient
import com.vj.sampleretrofitmvvm.viewmodel.RepoViewModel
import okhttp3.mockwebserver.MockResponse
import org.junit.Test

class GithubViewModelTest {
    val repository = GitRepository(RepoClient.create())
    val context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun repo_list() {
        NetworkBuilderMock.mockWebServer.enqueue(
            MockResponse().setBody(
                AndroidTestUtils.readFromFile(context, "repositories.json")
            )
        )
    }
}