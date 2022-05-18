package com.vj.sampleretrofitmvvm

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import com.vj.sampleretrofitmvvm.helper.ArrayListMoshiAdapter
import com.vj.sampleretrofitmvvm.network.GithubEndpoint
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Vijay on 18/05/2022.
 * https://github.com/thenameisvijay
 */
class GithubApiServiceTest {
    private lateinit var service: GithubEndpoint
    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi
                        .Builder()
                        .add(ArrayListMoshiAdapter())
                        .build()
                )
            ).build()
            .create(GithubEndpoint::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        val mockResponse = MockResponse()
        val fileStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = fileStream.source().buffer()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun `request github user data and get expected response`(){
        runBlocking {
            enqueueMockResponse("apiresponse.json")
            val responseBody = service.requestUserData()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/users")
        }
    }

    @Test
    fun `compare github user data and get expected response`(){
        runBlocking {
            enqueueMockResponse("apiresponse.json")
            val responseBody = service.requestUserData()
            assertThat(responseBody[0].login).isEqualTo("mojombo")//will pass
            assertThat(responseBody[0].avatar_url).isEqualTo("https://avatars.githubusercontent.com/u/1?v=4")//will pass
            assertThat(responseBody[0].url).contains("mojombo")//will pass
//            assertThat(responseBody[2].login).contains("defunkt")//fail expected to contain: defunkt but was  : pjhyett
        }
    }


    @After
    fun tearDown() {
        server.shutdown()
    }

}