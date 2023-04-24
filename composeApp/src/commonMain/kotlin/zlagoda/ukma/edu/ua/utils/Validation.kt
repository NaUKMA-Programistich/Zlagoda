package zlagoda.ukma.edu.ua.utils


import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter


fun String.isPhoneNumberValid(): Boolean {
    val regex = Regex("[+\\d]{1,13}")
    return this.matches(regex) && this.replace("+", "").length >= 5
}

fun String.isBDayValid () : Boolean{
    return try {
        val start = LocalDate.parse(this)
        val age = Period.between(start, LocalDate.now()).years
        age >= 18
    }
    catch (e: Exception) {
        false
    }
}

fun String.isStartDateValid () : Boolean{
    return try {
        val dateToCheck = LocalDate.parse(this)
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
