package com.route.data.repos

import com.route.data.dataSource.ProductsOnlineDataSource
import com.route.domain.data.Product
import com.route.domain.data.ProductsSort
import com.route.domain.data.Resource
import com.route.domain.repo.ProductsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepoImpl @Inject constructor(
    val productsOnlineDataSource: ProductsOnlineDataSource,
) : ProductsRepo {
    override fun getProducts(sort: ProductsSort): Flow<Resource<List<Product>?>> {
        return productsOnlineDataSource.getProducts(
            sort = sort,
        )
    }

    override fun searchProducts(keyword: String?): Flow<Resource<List<Product>?>> {
        return productsOnlineDataSource.searchProducts(
            keyword = keyword
        )
    }
}
