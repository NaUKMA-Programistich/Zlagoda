package zlagoda.ukma.edu.ua.utils.validation

import zlagoda.ukma.edu.ua.db.Category

class CategoryValidator: Validator<Category> {

    override fun validate(obj: Category) {
        validateTextPropertyWithMaxLength(obj.name, "name", 50)
    }
}