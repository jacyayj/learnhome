package pro.haichuang.learn.home.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.internal.LinkedTreeMap
import com.jacy.kit.config.toJson
import pro.haichuang.learn.home.bean.RowsBean

object GsonUtil {

    fun <T> parseRows(result: Any?, clazz: Class<T>): RowsBean<T> {
        val obj = JsonParser().parse(result?.toJson()).asJsonObject
        val rows = RowsBean<T>()
        val list = ArrayList<T>()
        rows.firstPage = obj.get("firstPage").asBoolean
        rows.firstResult = obj.get("firstResult").asInt
        rows.lastPage = obj.get("lastPage").asBoolean
        val gson = Gson()
        obj.getAsJsonArray("list")?.forEach {
            list.add(gson.fromJson(it, clazz))
        }
        rows.list = list
        return rows
    }

}