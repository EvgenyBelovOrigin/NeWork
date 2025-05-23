package ru.netology.nework.utils

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

object AndroidUtils {

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInputFromInputMethod(view.windowToken, 0)
    }

    fun Uri.getFile(context: Context): File? {
        val inputStream = context.contentResolver.openInputStream(this)
        val tempFile = File.createTempFile("temp", ".jpg")

        val outputStream = FileOutputStream(tempFile)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }

    fun dateUtcToStringDateTime(string: String): String {
        return ZonedDateTime.parse(string).withZoneSameInstant(ZoneId.systemDefault())
            .format(
                DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
            )
    }

    fun dateUtcToStringDate(string: String): String {
        return ZonedDateTime.parse(string).withZoneSameInstant(ZoneId.systemDefault())
            .format(
                DateTimeFormatter.ofPattern("dd.MM.yy")
            )
    }

    fun dateUtcToCalendar(dateStr: String): Calendar {
        val zonedDateTime = ZonedDateTime.parse(dateStr)
        val date = Date.from(zonedDateTime.toInstant())
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    //    @SuppressLint("SimpleDateFormat")
    fun calendarToUtcDate(calendar: Calendar): String {
        val date = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(date)
    }

}