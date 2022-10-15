package com.fidilaundry.app.util

import java.util.regex.Matcher
import java.util.regex.Pattern

object MaskUtils {
    fun MaskAll(value: String): String {
        val regex = "(?<=|^.{0,1}).(?=.*)"
        val pattern: Pattern = Pattern.compile(regex, Pattern.MULTILINE)
        val matcher: Matcher = pattern.matcher(value)
        return matcher.replaceAll("⬤")
    }

    fun MaskAcc(value: String): String {
        val regex = "(?<=|^.{0,1}).(?=.*...)"
        val pattern: Pattern = Pattern.compile(regex, Pattern.MULTILINE)
        val matcher: Matcher = pattern.matcher(value)
        return matcher.replaceAll("⬤")
    }

    fun MaskLast(value: String): String {
        var result = ""
        for (i in value.indices) {
            if (i < 4) {
                result += value[i]
            } else {
                result += "⬤"
            }
        }
        return result
    }
}