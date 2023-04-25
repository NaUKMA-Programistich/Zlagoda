package zlagoda.ukma.edu.ua.utils.validation

import zlagoda.ukma.edu.ua.db.CustomerCard

class CustomerCardValidator: Validator<CustomerCard> {

    override fun validate(obj: CustomerCard) {
        validateTextPropertyWithMaxLength(obj.custSurname, "surname",50 )
        validateTextPropertyWithMaxLength(obj.custName, "name",50 )
        validateTextPropertyWithMaxLength(obj.custPatronymic, "patronymic",50 )
        validateTextPropertyWithMaxLength(obj.city, "city",50 )
        validateTextPropertyWithMaxLength(obj.street, "stree",50 )
        validateTextPropertyWithMaxLength(obj.zipCode, "zip code",9 )
        validateNumberProperty(obj.percent, "percent")
    }

}