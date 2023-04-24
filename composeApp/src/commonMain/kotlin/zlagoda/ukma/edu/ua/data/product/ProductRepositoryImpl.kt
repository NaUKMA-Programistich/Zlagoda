package zlagoda.ukma.edu.ua.data.product

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import zlagoda.ukma.edu.ua.db.MyDatabase
import zlagoda.ukma.edu.ua.db.Product

class ProductRepositoryImpl(
    db: MyDatabase
): ProductRepository {

    private val queries = db.productQueries

    override suspend fun getProductByIdProduct(idProduct: Long): Product? {
        return withContext(Dispatchers.IO) {
            queries.getProductByIdProduct(idProduct).executeAsOneOrNull()
        }
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return queries.getAllProducts().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun deleteProductByIdProduct(idProduct: Long) {
        return withContext(Dispatchers.IO) {
            queries.deleteProductByIdProduct(idProduct)
        }
    }

    override suspend fun insertProduct(product: Product) {
        return withContext(Dispatchers.IO) {
            queries.insertProduct(
                idProduct =  product.idProduct.takeIf { it != -1L },
                categoryNumber = product.categoryNumber,
                productName = product.productName,
                characteristics = product.characteristics
            )
        }
    }
}