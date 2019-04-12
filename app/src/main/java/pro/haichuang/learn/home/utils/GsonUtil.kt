package pro.haichuang.learn.home.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
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

    fun <T> parseObject(result: Any?, clazz: Class<T>): T = Gson().fromJson<T>(result?.toJson(), clazz)

    fun <T> parseArray(result: Any?, clazz: Class<T>): ArrayList<T> {
        val array = ArrayList<T>()
        val gson = Gson()
        JsonParser().parse(result?.toJson()).asJsonArray?.forEach {
            array.add(gson.fromJson(it, clazz))
        }
        return array
    }

    fun getString(result: Any?, key: String) = JsonParser().parse(result?.toJson())?.asJsonObject?.get(key)?.asString
            ?: ""
}