package com.vj.sampleretrofitmvvm

import android.content.Context
import java.io.BufferedReader

object AndroidTestUtils {
    @JvmStatic
    fun readFromFile(context: Context, jsonFileName: String): String {
        val jsonInputStream = context.assets.open(jsonFileName)
        return jsonInputStream.bufferedReader().use(BufferedReader::readText)
    }
}