package com.route.domain.usecase

import com.route.domain.data.Product
import com.route.domain.data.Resource
import com.route.domain.repo.ProductsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    val productsRepo: ProductsRepo
) {
    fun searchProducts(
        keyword: String?
    ): Flow<Resource<List<Product>?>> {
        return productsRepo.searchProducts(
            keyword = keyword
        )
    }

    suspend operator fun invoke(
        keyword: String?
    ): Flow<Resource<List<Product>?>> {
        return productsRepo.searchProducts(
            keyword = keyword
        )
    }
}
