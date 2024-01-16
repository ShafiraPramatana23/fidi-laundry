package com.fidilaundry.app.util

import com.fidilaundry.app.R

class ServiceCtgHelper {
    fun getServiceName(id: String): String {
        var result = ""
        when (id) {
            "1" -> result = "Cuci Kering"
            "2" -> result = "Cuci Basah"
            "3" -> result = "Cuci Setrika"
            "4" -> result = "Setrika"
        }
        return result
    }

    fun getImgService(id: Int): Int {
        var result = 0
        when (id) {
            1 -> result = R.drawable.ic_cuker
            2 -> result = R.drawable.ic_cuba
            3 -> result = R.drawable.ic_cusek
            4 -> result = R.drawable.ic_setrika
        }
        return result
    }

    fun getCtgName(id: String): String {
        var result = ""
        when (id) {
            "1" -> result = "Satuan"
            "2" -> result = "Kiloan"
        }
        return result
    }
}