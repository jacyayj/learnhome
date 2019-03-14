package pro.haichuang.learn.home.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.jacy.kit.config.toast
import com.vondear.rxtool.RxEncryptTool
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.bean.Response
import pro.haichuang.learn.home.net.Url
import java.io.File

object HttpUtils {

    fun upLoadFile(path: String, result: (path: String) -> Unit) {
        val params = HttpParams()
        params.put("mobile", "18384124448")
        params.put("type", "image")
        params.put("appId", "5698402822576322")
        params.put("nonce_str", System.currentTimeMillis().toString())
        params.put("sign", sign(params.urlParamsMap))
        params.put("uploadFile", File(path), null)
        EasyHttp.post(Url.Upload.Upload)
                .sign(true)
                .params(params)
                .execute(object : SimpleCallBack<String>() {
                    override fun onSuccess(t: String?) {
                        val response = Gson().fromJson<Response<JsonObject>>(t, object : TypeToken<Response<JsonObject>>() {}.type)
                        if (response.code == 200)
                            result(response?.body?.get("uploadPath")?.asString ?: "")
                        else
                            toast(response.message + "   " + response.code)
                    }

                    override fun onError(e: ApiException?) {
                        toast(e?.message)
                    }
                })
    }

    fun release() {

    }

    private fun sign(map: LinkedHashMap<String, String>): String {
        var sign = ""
        map.keys.toSortedSet().forEach {
            if ("uploadFile" != it && !map[it].isNullOrEmpty())
                sign += "$it=${map[it]}&"
        }
        sign += "key=${Url.app_key}"
        return RxEncryptTool.encryptMD5ToString(sign)
    }
}