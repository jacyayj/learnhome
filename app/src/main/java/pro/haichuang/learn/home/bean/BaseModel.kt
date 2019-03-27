package pro.haichuang.learn.home.bean

import android.databinding.BaseObservable
import com.jacy.kit.net.Params
import com.vondear.rxtool.RxEncryptTool
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.net.Url

open class BaseModel : BaseObservable() {

    fun getParams(url: String): HttpParams {
        val params = HttpParams()
        javaClass.declaredFields.forEach { field ->
            if (field.isAnnotationPresent(Params::class.java)) {
                val present = field.getAnnotation(Params::class.java)
                if (present.url.contains(url)) {
                    field.isAccessible = true
                    when {
                        "password" == present.name -> params.put(present.name, RxEncryptTool.encryptMD5ToString(field.get(this).toString()).toLowerCase())
                        url == Url.User.Login && "mobile" == present.name -> params.put("username", field.get(this).toString())
                        else -> params.put(present.name, field.get(this).toString())
                    }
                }
            }
        }
        return params
    }

    var JSESSIONID = ""

    open fun checkSuccess(url: String) = true

}