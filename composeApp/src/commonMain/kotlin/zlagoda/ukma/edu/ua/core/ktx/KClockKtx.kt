package zlagoda.ukma.edu.ua.core.ktx

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz

fun Int.wrap(): String = if (this < 10) "0$this" else this.toString()

fun getValueOrNull(value: String?): DateTimeTz? {
    return if (value == null) {
        null
    } else {
        DateTime.fromString(value)
    }
}

fun DateTime.toStr(): String = this.toString("yyyy-MM-dd")