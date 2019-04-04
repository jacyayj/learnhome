package pro.haichuang.learn.home.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object FileUtils {

    fun readProvince(context: Context) = readAssets(context, "province.text")

    fun readAssets(context: Context, name: String): String {
        try {
            val inputStreamReader = InputStreamReader(context.resources.assets.open(name), "UTF-8")
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String?
            var result = ""
            while (true) {
                line = bufferedReader.readLine()
                if (line.isNullOrEmpty())
                    break
                result += line
            }
            return result
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

}