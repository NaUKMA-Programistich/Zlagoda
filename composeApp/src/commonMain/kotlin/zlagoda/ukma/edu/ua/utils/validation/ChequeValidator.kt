package zlagoda.ukma.edu.ua.utils.validation

import zlagoda.ukma.edu.ua.db.Cheque

class ChequeValidator: Validator<Cheque> {

    override fun validate(obj: Cheque) {
        if(obj.printDate.isStartDateValid())
            throw InvalidModelException("The print date is not valid")
        validateNumberProperty(obj.sumTotal, "total sum")
        validateNumberProperty(obj.vat, "vat")
    }

}