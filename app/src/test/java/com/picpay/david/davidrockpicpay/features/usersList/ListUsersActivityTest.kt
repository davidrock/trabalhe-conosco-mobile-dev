package com.picpay.david.davidrockpicpay.features.usersList

import com.picpay.david.davidrockpicpay.api.IPicPayAPI
import com.picpay.david.davidrockpicpay.api.PicPayAPI
import com.picpay.david.davidrockpicpay.models.User
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class ListUsersActivityTest {

    lateinit var mockServer : MockWebServer
    lateinit var api: IPicPayAPI

    @Before
    @Throws fun setUp() {
        // Initialize mock webserver
        mockServer = MockWebServer()
        // Start the local server
        mockServer.start()

        // Get an okhttp client
        val okHttpClient = OkHttpClient.Builder()
                .build()

        // Get an instance of Retrofit
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://careers.picpay.com/tests/mobdev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        api = retrofit.create(IPicPayAPI::class.java)
    }

    @After
    @Throws fun tearDown() {
        // We're done with tests, shut it down
        mockServer.shutdown()
    }

    @Test fun fillListShouldReturnsAUserList() {
        val testObserver = TestObserver<List<User>>()

        val path = "/users"

        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(getJson("json/mockUsers.json"))
        // Enqueue request
        mockServer.enqueue(mockResponse)

        // Call the API
        api.GetUsers().subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // No errors
        testObserver.assertNoErrors()
        // One list emitted
        testObserver.assertValueCount(1)

        testObserver.assertComplete()

        assertNotNull(mockResponse.body)

    }


    fun getJson(path : String) : String {
        // Load the JSON response
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

}