package com.fidilaundry.app.util

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

    fun getCtgName(id: String): String {
        var result = ""
        when (id) {
            "1" -> result = "Satuan"
            "2" -> result = "Kiloan"
        }
        return result
    }
}