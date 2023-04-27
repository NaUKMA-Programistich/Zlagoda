package zlagoda.ukma.edu.ua.data.store_product

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.GetAllStoreProductsWithNames
import zlagoda.ukma.edu.ua.db.GetStoreProductDescriptionByUPC
import zlagoda.ukma.edu.ua.db.StoreProduct

interface StoreProductRepository {

    suspend fun getStoreProductByUPC(upc: String): StoreProduct?

    fun getAllStoreProductsWithNames(): Flow<List<GetAllStoreProductsWithNames>>
    fun getAllStoreProducts(): Flow<List<StoreProduct>>

    suspend fun deleteStoreProductByUPC(upc: String)

    suspend fun insertStoreProduct(storeProduct: StoreProduct)

    suspend fun searchByProductName(productName: String): List<StoreProduct>
    suspend fun searchByCategoryName(categoryName: String): List<StoreProduct>

    suspend fun getStoreProductsByIdProduct(idProduct: Long): List<StoreProduct>

    suspend fun getStoreProductDescriptionByUPC(upc: String): GetStoreProductDescriptionByUPC?

    fun getStoreProductsSortedByProductsNumber(): Flow<List<StoreProduct>>
    fun getPromotionalStoreProductsSortedByName(): Flow<List<StoreProduct>>
    fun getPromotionalStoreProductsSortedByProductsNumber(): Flow<List<StoreProduct>>
    fun getNotPromotionalStoreProductsSortedByName(): Flow<List<StoreProduct>>
    fun getNotPromotionalStoreProductsSortedByProductsNumber(): Flow<List<StoreProduct>>
}