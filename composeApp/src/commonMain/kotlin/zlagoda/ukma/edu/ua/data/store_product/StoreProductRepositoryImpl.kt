package zlagoda.ukma.edu.ua.data.store_product

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import zlagoda.ukma.edu.ua.db.MyDatabase
import zlagoda.ukma.edu.ua.db.StoreProduct
import java.util.*

class StoreProductRepositoryImpl(
    db: MyDatabase
): StoreProductRepository {

    private val queries = db.storeProductQueries

    override suspend fun getStoreProductByUPC(upc: String): StoreProduct? {
        return withContext(Dispatchers.IO) {
            queries.getStoreProductByUPC(upc).executeAsOneOrNull()
        }
    }

    override fun getAllStoreProducts(): Flow<List<StoreProduct>> {
        return queries.getAllStoreProducts().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun deleteStoreProductByUPC(upc: String) {
        withContext(Dispatchers.IO) {
            queries.deleteStoreProductByUPC(upc)
        }
    }

    override suspend fun insertStoreProduct(storeProduct: StoreProduct) {
        withContext(Dispatchers.IO) {
            queries.insertStoreProduct(
                upc = storeProduct.upc.ifBlank { UUID.randomUUID().toString() },
                upcProm = storeProduct.upcProm,
                idProduct = storeProduct.idProduct,
                sellingPrice = storeProduct.sellingPrice,
                productsNumber = storeProduct.productsNumber,
                promotionalProduct = storeProduct.promotionalProduct
            )
        }
    }
}