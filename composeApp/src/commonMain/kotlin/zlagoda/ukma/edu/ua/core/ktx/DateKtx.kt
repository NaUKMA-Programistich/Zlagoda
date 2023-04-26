package zlagoda.ukma.edu.ua.core.ktx

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Date


private val PATTERN = "yyyy-MM-dd"

fun Date.toStr(): String = SimpleDateFormat(PATTERN).format(this)

fun String.toDate(): Date? {
    return runCatching {
        SimpleDateFormat(PATTERN).parse(this)
    }.getOrNull()
}