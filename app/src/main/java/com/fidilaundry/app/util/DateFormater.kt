package com.fidilaundry.app.util

import com.esotericsoftware.minlog.Log.info
import org.joda.time.DateTimeComparator
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun DateFormater(string: String): String {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("UTC")
    calendar.time = convertedDate
    val time = calendar.time
    val outputFmt =
        SimpleDateFormat("yyyy-MM-dd")
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}

fun dateCheck(string: String, lang: String): Boolean {
    var dateFormat: SimpleDateFormat? = null
    dateFormat = if (lang == "in") {
        SimpleDateFormat("d MMMM yyyy", Locale("ID"))
    } else {
        SimpleDateFormat("MMMM dd, yyyy")
    }
    var convertedDate: Date? = Date()

    //try {
        convertedDate = dateFormat.parse(string)
    //} catch (e: ParseException) {
    //    e.printStackTrace()
    //}

    var convertedDate2: Date? = Date()
    //try {
        convertedDate2 = dateFormat.parse(getTodayDateWithMonthName())
//    } catch (e: ParseException) {
//        e.printStackTrace()
//    }

    val compareDateVal = DateTimeComparator.getDateOnlyInstance().compare(convertedDate2, convertedDate)

    return compareDateVal <= 0
}

fun DateFormaterMonthNickName(string: String, lang: String): String {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("UTC")
    calendar.time = convertedDate
    val time = calendar.time

    return if (lang == "in") {
        val outputFmt =
            SimpleDateFormat("dd MMM yyyy", Locale("ID"))
        outputFmt.format(time).toString()
    } else {
        val outputFmt =
            SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
        outputFmt.format(time).toString()
    }
}

fun getTodayDateWithMonthName(): String {
    var todayDate: Date? = Date()

    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("UTC")
    calendar.time = todayDate
    val time = calendar.time
    val outputFmt =
        SimpleDateFormat("dd MMMM yyyy", Locale("ID"))
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}

fun DateFormaterMonthName(string: String, lang: String): String {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("UTC")
    calendar.time = convertedDate

    return if (lang == "in") {
        val outputFmt =
            SimpleDateFormat("dd MMMM yyyy", Locale("ID"))
        outputFmt.format(convertedDate)
    } else {
        val outputFmt =
            SimpleDateFormat("MMMM dd, yyyy", Locale("ID"))
        outputFmt.format(convertedDate)
    }
}

fun DateFormaterFromMonthName(string: String): String {
    val dateFormat =
        SimpleDateFormat("dd MMMM yyyy", Locale("ID"))
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("UTC")
    calendar.time = convertedDate
    val time = calendar.time
    val outputFmt =
        SimpleDateFormat("yyyy-MM-dd")
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}

fun DateTimeFormater(string: String): String {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("Etc/GMT")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.time = convertedDate
    val time = calendar.time
    val outputFmt =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}

fun DateTimeValidityFormater(string: String): String {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("Etc/GMT")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.time = convertedDate
    val time = calendar.time
    val outputFmt =
        SimpleDateFormat("yyyy/MM/dd")
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}

fun DateTimeReportFormater(string: String): String {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("Etc/GMT")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.time = convertedDate
    val time = calendar.time
    val outputFmt =
        SimpleDateFormat("yyyy/MM/dd HH:mm")
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}

fun MonthNameFormater(string: String): String {
    val dateFormat =
        SimpleDateFormat("MM")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("UTC")
    calendar.time = convertedDate
    val time = calendar.time
    val outputFmt =
        SimpleDateFormat("MMM", Locale("ID"))
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}

fun getNameMonthFormater(string: String, lang: String): String {
    val dateFormat =
        SimpleDateFormat("M", Locale("ID"))
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("UTC")
    calendar.time = convertedDate

    return if (lang == "in") {
        val outputFmt =
            SimpleDateFormat("MMMM", Locale("ID"))
        outputFmt.format(convertedDate)
    } else {
        val outputFmt =
            SimpleDateFormat("MMMM")
        outputFmt.format(convertedDate)
    }
}

fun MonthNumberFormater(string: String, lang: String): String {
    val dateFormat =
        SimpleDateFormat("MMMM yyyy")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("UTC")
    calendar.time = convertedDate

    return if (lang == "in") {
        val outputFmt =
            SimpleDateFormat("M", Locale("ID"))
        outputFmt.format(convertedDate)
    } else {
        val outputFmt =
            SimpleDateFormat("M")
        outputFmt.format(convertedDate)
    }
}

fun YearNumberFormater(string: String): String {
    val dateFormat =
        SimpleDateFormat("MMMM yyyy")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("UTC")
    calendar.time = convertedDate
    val time = calendar.time
    val outputFmt =
        SimpleDateFormat("yyyy", Locale("ID"))
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}

fun ChatDateFormater(string: String): String {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("Etc/GMT")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    var outputFmt: SimpleDateFormat

    if (convertedDate?.time == Date().time) {
        outputFmt = SimpleDateFormat("HH:MM")
    } else {
        outputFmt = SimpleDateFormat("yyyy-MM-dd HH:mm")
    }

    return outputFmt.format(convertedDate)
}

fun MatahariFormater(string: String): String {
    val df = SimpleDateFormat("yyyy-MM-dd hh:mm:ssZ")
    var result: Date
    var output: String? = ""
    try {
        result = df.parse(string)
        info("date:$result") //prints date in current locale

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.timeZone = TimeZone.getTimeZone("GMT+7:00")
        output = sdf.format(result)
        info(sdf.format(result)) //prints date in the format sdf
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return output.toString()
}

fun DateTimeNotifFormater(string: String): String {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("Etc/GMT")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.time = convertedDate
    val time = calendar.time
    val outputFmt =
        SimpleDateFormat("dd MMM yyyy HH:mm:ss")
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}

fun DateTradeFormater(string: String): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val outputFmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val utcTime = outputFmt.format(convertedDate)

    return utcTime.toString()
}

fun DateTimeTradeFormater(string: String): String {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("Etc/GMT")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.time = convertedDate
    val time = calendar.time
    val outputFmt =
        SimpleDateFormat("yyyy-MM-dd")
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}

fun DateFormaterHistory(string: String, lang: String): String {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("Etc/GMT")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.time = convertedDate

    return if (lang == "in") {
        val outputFmt =
            SimpleDateFormat("dd MMM yyyy", Locale("ID"))
        outputFmt.format(convertedDate)
    } else {
        val outputFmt =
            SimpleDateFormat("dd MMM yyyy")
        outputFmt.format(convertedDate)
    }
}

fun dateFormatterDetail(string: String, lang: String): String {
    val dateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("Etc/GMT")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.time = convertedDate

    return if (lang == "in") {
        val outputFmt =
                SimpleDateFormat("yyyy/MM/dd HH:mm", Locale("ID"))
        outputFmt.format(convertedDate)
    } else {
        val outputFmt =
                SimpleDateFormat("yyyy/MM/dd HH:mm")
        outputFmt.format(convertedDate)
    }
}