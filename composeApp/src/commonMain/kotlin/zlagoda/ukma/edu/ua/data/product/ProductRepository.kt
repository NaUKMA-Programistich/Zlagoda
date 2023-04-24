package zlagoda.ukma.edu.ua.data.product

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.Product

interface ProductRepository {

    suspend fun getProductByIdProduct(idProduct: Long): Product?

    fun getAllProducts(): Flow<List<Product>>

    suspend fun deleteProductByIdProduct(idProduct: Long)

    suspend fun insertProduct(product: Product)
}