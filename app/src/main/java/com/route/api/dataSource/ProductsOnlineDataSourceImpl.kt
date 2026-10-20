package com.route.api.dataSource

import com.route.api.ApiServices
import com.route.api.data.toProductsSortDto
import com.route.api.helpers.safeApiCallV2
import com.route.data.dataSource.ProductsOnlineDataSource
import com.route.domain.data.Product
import com.route.domain.data.ProductsSort
import com.route.domain.data.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsOnlineDataSourceImpl @Inject constructor(
    val apiServices: ApiServices,
): ProductsOnlineDataSource {
    override fun getProducts(sort: ProductsSort): Flow<Resource<List<Product>?>> {

        return safeApiCallV2(
            api = {
                apiServices.getProducts(sort = sort.toProductsSortDto())
            },
            converter = { dtos ->
                dtos?.map { dto -> dto.toProduct() }?:listOf()
            }
        )
    }

    override fun searchProducts(keyword: String?): Flow<Resource<List<Product>?>> {
        return safeApiCallV2(
            api = {
                apiServices.searchProducts(keyword = keyword)
            },
            converter = { dtos ->
                dtos?.map { dto -> dto.toProduct() } ?: listOf()
            }
        )
    }
}
