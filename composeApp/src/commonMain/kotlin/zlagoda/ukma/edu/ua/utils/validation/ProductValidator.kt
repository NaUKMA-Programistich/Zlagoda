package zlagoda.ukma.edu.ua.utils.validation

import zlagoda.ukma.edu.ua.db.Product

class ProductValidator: Validator<Product> {

    override fun validate(obj: Product) {
        validateTextPropertyWithMaxLength(obj.productName, "product name", 50)
        validateTextPropertyWithMaxLength(obj.characteristics, "characteristics", 100)
    }
}