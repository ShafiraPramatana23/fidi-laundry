package com.fidilaundry.app.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun DateTimeFormater(string: String): String {
    val dateStr = string.split(".")[0]
    val dateSplit = dateStr.split("T")
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var convertedDate: Date? = Date()
    try {
        convertedDate = dateFormat.parse("${dateSplit[0]} ${dateSplit[1]}")
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val calendar = Calendar.getInstance()
    calendar.time = convertedDate
    val time = calendar.time
    val outputFmt = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("ID"))
    val utcTime = outputFmt.format(time)

    return utcTime.toString()
}
