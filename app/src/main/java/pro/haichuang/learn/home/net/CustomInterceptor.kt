package pro.haichuang.learn.home.net

import com.vondear.rxtool.RxEncryptTool
import com.zhouyou.http.interceptor.BaseDynamicInterceptor
import java.util.*

class CustomInterceptor : BaseDynamicInterceptor<CustomInterceptor>() {
    override fun dynamic(dynamicMap: TreeMap<String, String>): TreeMap<String, String> {
        dynamicMap["appId"] = "5698402822576322"
        dynamicMap["nonce_str"] = System.currentTimeMillis().toString()
        dynamicMap["sign"] = sign(dynamicMap)
        return dynamicMap
    }

    private fun sign(map: TreeMap<String, String>): String {
        var sign = ""
        map.keys.toSortedSet().forEach {
            sign += "$it=${map[it]}&"
        }
        sign += "key=${Url.app_key}"
        return RxEncryptTool.encryptMD5ToString(sign)
    }
}