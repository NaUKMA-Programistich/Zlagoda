package zlagoda.ukma.edu.ua.core.ktx

import java.security.MessageDigest
import kotlin.experimental.and


fun String.encrypt(): String {
    val messageDigest = MessageDigest.getInstance("MD5")
    messageDigest.update(this.encodeToByteArray())

    val bytes: ByteArray = messageDigest.digest()

    val result = StringBuilder()
    for (element in bytes) {
        result.append(((element and 0xff.toByte()) + 0x100).toString(16).substring(1))
    }
    return result.toString()
}