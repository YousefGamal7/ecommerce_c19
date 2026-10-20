package com.route.api

import com.route.api.helpers.SearchInterceptor
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchApiTest {

    @Test
    fun testSearchProductsEndpoint() = runBlocking {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(SearchInterceptor())
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://ecommerce.routemisr.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiServices = retrofit.create(ApiServices::class.java)

        // Test searching for Shawl
        val response = apiServices.searchProducts(keyword = "Shawl")
        assertNotNull(response)
        assertNotNull(response.data)
        assertTrue(response.data!!.isNotEmpty())
        assertTrue(response.data!!.all { product ->
            product.title?.contains("Shawl", ignoreCase = true) == true ||
            product.description?.contains("Shawl", ignoreCase = true) == true
        })
    }
}
