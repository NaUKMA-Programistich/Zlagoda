package zlagoda.ukma.edu.ua.utils.validation

interface Validator<T> {

    fun validate(obj: T)
}

fun validateTextPropertyWithMaxLength(value: String, name: String, maxLength: Int) {
    if(value.isBlank())
        throw InvalidModelException("The $name must be not blank")
    if(value.length > maxLength)
        throw InvalidModelException("The $name must contain up to 50 characters")
}

fun validateTextPropertyWithMaxLength(value: String?, name: String, maxLength: Int) {
    value?.let {
        if(it.isBlank())
            throw InvalidModelException("The $name must be not blank")
        if(it.length > maxLength)
            throw InvalidModelException("The $name must contain up to 50 characters")
    }
}

fun validateNumberProperty(value: Number, name: String) {
    when(value) {
        is Long -> {
            if(value < 0) {
                throw InvalidModelException("The $name can't be negative")
            }
        }
        is Double -> {
            if(value < 0) {
                throw InvalidModelException("The $name can't be negative")
            }
        }
        else -> {}
    }
}

class InvalidModelException(message: String): Exception(message)