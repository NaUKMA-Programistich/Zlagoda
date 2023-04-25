package zlagoda.ukma.edu.ua.data.store_product

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.StoreProduct

interface StoreProductRepository {

    suspend fun getStoreProductByUPC(upc: String): StoreProduct?

    fun getAllStoreProducts(): Flow<List<StoreProduct>>

    suspend fun deleteStoreProductByUPC(upc: String)

    suspend fun insertStoreProduct(storeProduct: StoreProduct)

    suspend fun searchByProductName(productName: String): List<StoreProduct>
    suspend fun searchByCategoryName(categoryName: String): List<StoreProduct>

    suspend fun getStoreProductsByIdProduct(idProduct: Long): List<StoreProduct>
}