package zlagoda.ukma.edu.ua.utils.validation

import zlagoda.ukma.edu.ua.db.CustomerCard

class CustomerCardValidator: Validator<CustomerCard> {

    override fun validate(obj: CustomerCard) {
        validateTextPropertyWithMaxLength(obj.custSurname, "surname",50 )
        validateTextPropertyWithMaxLength(obj.custName, "name",50 )
        validateNullableTextPropertyWithMaxLength(obj.custPatronymic, "patronymic",50 )

        if(!obj.phoneNumber.isPhoneNumberValid())
            throw InvalidModelException("The phone number must contain up to 13 numbers(including +)")

        validateNullableTextPropertyWithMaxLength(obj.city, "city",50 )
        validateNullableTextPropertyWithMaxLength(obj.street, "stree",50 )
        validateNullableTextPropertyWithMaxLength(obj.zipCode, "zip code",9 )
        validateNumberProperty(obj.percent, "percent")
    }

}