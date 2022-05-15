package com.mash_up.mvvmstudy

import com.mash_up.mvvmstudy.repository.model.Owner
import com.mash_up.mvvmstudy.repository.model.Repositories
import com.mash_up.mvvmstudy.repository.model.Repository
import com.mash_up.mvvmstudy.repository.remote.GitService
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var service: GitService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        initMockWebServer()
    }

    @After
    fun close() {
        mockWebServer.shutdown()
    }

    private fun initMockWebServer() {
        initClient()
    }

    private fun initClient() {
        val client = OkHttpClient.Builder().apply {
            connectTimeout(1, TimeUnit.SECONDS)
            writeTimeout(1, TimeUnit.SECONDS)
            readTimeout(1, TimeUnit.SECONDS)
        }.build()

        val api = Retrofit.Builder().apply {
            baseUrl(mockWebServer.url("/"))
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()

        service = api.create(GitService::class.java)
    }

    @Test
    fun getRepositories_isCorrect() {
        mockWebServer.enqueueResponse("response.json", 200)

        val actual = service.getRepositories("test").execute().body()
        val expected =
            Repositories(
                totalCount = 40,
                incompleteResult = false,
                repositories = listOf(
                    Repository(
                        id = 3081286,
                        name = "Tetris",
                        fullName = "dtrupenn/Tetris",
                        owner = Owner(
                            id = 872147,
                            login = "dtrupenn",
                            avatarUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png"
                        ),
                        language = "Assembly",
                        description = "A C implementation of Tetris using Pennsim through LC4",
                        stargazersCount = 1,
                        updatedAt = "2013-01-05T17:58:47Z"
                    )
                )
            )

        assertEquals(expected, actual)
    }
}