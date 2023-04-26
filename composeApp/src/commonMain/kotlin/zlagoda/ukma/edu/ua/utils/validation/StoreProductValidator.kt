package zlagoda.ukma.edu.ua.utils.validation

import kotlinx.coroutines.runBlocking
import zlagoda.ukma.edu.ua.db.StoreProduct
import zlagoda.ukma.edu.ua.di.Injection

class StoreProductValidator: Validator<StoreProduct> {

    private val storeProductsRepository = Injection.storeProductRepository

    override fun validate(obj: StoreProduct) {
        validateForProduct(obj)
        validateNumberProperty(obj.productsNumber, "products number")
        validateNumberProperty(obj.sellingPrice, "Selling price")
    }

    private fun validateForProduct(obj: StoreProduct) {
        runBlocking {
            val thisStoreProduct = storeProductsRepository.getStoreProductByUPC(obj.upc)
            thisStoreProduct?.let {
                // We just edit existing store product
                return@runBlocking
            }
            val storeProducts = storeProductsRepository.getStoreProductsByIdProduct(obj.idProduct)
            if (storeProducts.size > 1)  throw InvalidModelException("There are already 2 store product for this product")
            if (storeProducts.size == 1) {
                if(storeProducts[0].promotionalProduct>0 && obj.promotionalProduct > 0) {
                    throw InvalidModelException("Promotional product for this product type already exist")
                }
                if(storeProducts[0].promotionalProduct <= 0 && obj.promotionalProduct <= 0) {
                    throw InvalidModelException("Not promotional product for this product type already exist")
                }
            }
        }
    }

}