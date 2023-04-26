package zlagoda.ukma.edu.ua.core.file

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


suspend fun buildReport(content: String) = withContext(Dispatchers.IO) {
    val time = System.currentTimeMillis()
    val filePath = "../Report_Zlagoda_$time.csv"
    val file = File(filePath).apply { createNewFile() }

    val outputStream = FileOutputStream(filePath)
    val strToBytes: ByteArray = content.encodeToByteArray()
    outputStream.write(strToBytes)
    outputStream.close()

    val desktop = Desktop.getDesktop();
    try {
        desktop.open(file)
        desktop.print(file)
    } catch (exception: IOException) {
        exception.printStackTrace()
    }
}