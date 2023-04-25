package zlagoda.ukma.edu.ua.utils.validation

import zlagoda.ukma.edu.ua.db.CustomerCard
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.utils.isBDayValid
import zlagoda.ukma.edu.ua.utils.isPhoneNumberValid
import zlagoda.ukma.edu.ua.utils.isStartDateValid

class EmployeeValidator: Validator<Employee> {

    override fun validate(obj: Employee) {
        validateTextPropertyWithMaxLength(obj.empl_surname, "surname",50 )
        validateTextPropertyWithMaxLength(obj.empl_name, "name",50 )
        validateTextPropertyWithMaxLength(obj.empl_patronymic, "patronymic",50 )
        validateTextPropertyWithMaxLength(obj.empl_role, "role",10 )
        validateTextPropertyWithMaxLength(obj.city, "city",50 )
        validateTextPropertyWithMaxLength(obj.street, "stree",50 )
        validateTextPropertyWithMaxLength(obj.zip_code, "zip code",9 )
        if(!obj.phone_number.isPhoneNumberValid())
            throw InvalidModelException("The phone number must contain up to 13 numbers(including +)")
        if(obj.date_of_birth.isBDayValid())
            throw InvalidModelException("The birthdate is not valid")
        if(obj.date_of_start.isStartDateValid())
            throw InvalidModelException("The start date is not valid")
        validateNumberProperty(obj.salary, "salary")
    }

}