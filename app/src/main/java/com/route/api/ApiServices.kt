package com.route.api

import com.route.api.data.BaseResponse
import com.route.api.data.CategoryDto
import com.route.api.data.ProductDto
import com.route.api.data.ProductsSortTypeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("v1/categories")
    suspend fun getCategories(): BaseResponse<List<CategoryDto>>

    @GET("v1/products")
    suspend fun getProducts(
        @Query("sort") sort: ProductsSortTypeDto,
    ): BaseResponse<List<ProductDto>>

    @GET("v1/products/search")
    suspend fun searchProducts(
        @Query("keyword") keyword: String? = null,
    ): BaseResponse<List<ProductDto>>

}
