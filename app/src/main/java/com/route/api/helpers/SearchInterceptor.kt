package com.route.api.helpers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.route.api.data.BaseResponse
import com.route.api.data.ProductDto
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class SearchInterceptor @Inject constructor() : Interceptor {

    private val gson = Gson()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url
        val path = url.encodedPath

        // Check if this is the search endpoint: /api/v1/products/search or v1/products/search
        if (path.contains("products/search")) {
            // First, attempt the original request against the server
            val originalResponse = chain.proceed(originalRequest)

            // If the server handles it successfully (HTTP 200..299), return it directly
            if (originalResponse.isSuccessful) {
                return originalResponse
            }

            // Route Misr Express backend returns 400 Bad Request ("Invalid ID") for /products/search
            // Close the failed response before proceeding
            originalResponse.close()

            // Extract search keyword from common query parameters
            val keyword = url.queryParameter("keyword")
                ?: url.queryParameter("q")
                ?: url.queryParameter("title")
                ?: url.queryParameter("search")
                ?: ""

            // Fallback: Fetch products from the main products endpoint
            val fallbackUrl = url.newBuilder()
                .encodedPath(path.replace("products/search", "products"))
                .removeAllQueryParameters("keyword")
                .removeAllQueryParameters("q")
                .removeAllQueryParameters("title")
                .removeAllQueryParameters("search")
                .addQueryParameter("limit", "100")
                .build()

            val fallbackRequest = originalRequest.newBuilder()
                .url(fallbackUrl)
                .build()

            val fallbackResponse = chain.proceed(fallbackRequest)
            if (!fallbackResponse.isSuccessful) {
                return fallbackResponse
            }

            val responseBodyString = fallbackResponse.body?.string() ?: ""
            fallbackResponse.close()

            val type = object : TypeToken<BaseResponse<List<ProductDto>>>() {}.type
            val baseResponse: BaseResponse<List<ProductDto>>? = try {
                gson.fromJson(responseBodyString, type)
            } catch (e: Exception) {
                null
            }

            val allProducts = baseResponse?.data ?: emptyList()
            val filteredProducts = if (keyword.isBlank()) {
                allProducts
            } else {
                allProducts.filter { product ->
                    product.title?.contains(keyword, ignoreCase = true) == true ||
                    product.description?.contains(keyword, ignoreCase = true) == true ||
                    product.category?.name?.contains(keyword, ignoreCase = true) == true ||
                    product.brandDto?.name?.contains(keyword, ignoreCase = true) == true
                }
            }

            val searchResultResponse = BaseResponse(
                metadata = baseResponse?.metadata,
                results = filteredProducts.size,
                data = filteredProducts
            )

            val jsonOutput = gson.toJson(searchResultResponse)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val newBody = jsonOutput.toResponseBody(mediaType)

            return Response.Builder()
                .request(originalRequest)
                .protocol(okhttp3.Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(newBody)
                .build()
        }

        return chain.proceed(originalRequest)
    }
}
