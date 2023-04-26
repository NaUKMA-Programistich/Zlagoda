package zlagoda.ukma.edu.ua.utils.validation


import zlagoda.ukma.edu.ua.core.ktx.toDate
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Date


fun String.isPhoneNumberValid(): Boolean {
    val regex = Regex("[+\\d]{1,13}")
    return this.matches(regex) && this.replace("+", "").length >= 5
}

// TODO
fun Date?.isBDayValid () : Boolean {
    if (this == null) return false
    return try {
        val start: LocalDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val age = Period.between(start, LocalDate.now()).years
        age >= 18
    }
    catch (e: Exception) {
        false
    }
}

fun Date?.isStartDateValid () : Boolean{
    if (this == null) return false

    return try {
        val dateToCheck = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val currentDate = LocalDate.now()
        return !dateToCheck.isAfter(currentDate)
    }
    catch (e: Exception) {
        false
    }
}


fun String.isZipCodeValid (): Boolean {
    return try {
        val number = this.toInt()
        return number >= 0
    }
    catch (e: Exception) {
        false
    }
}
