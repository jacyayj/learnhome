package pro.haichuang.learn.home.bean

import android.databinding.BaseObservable
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.net.Params

open class BaseModel : BaseObservable() {

    fun getParams(url: String): HttpParams {
        val params = HttpParams()
        javaClass.declaredFields.forEach { field ->
            if (field.isAnnotationPresent(Params::class.java)) {
                val present = field.getAnnotation(Params::class.java)
                if (present.url.contains(url)) {
                    field.isAccessible = true
                    params.put(present.name, field.get(this).toString())
                }
            }
        }
        return params
    }

    open fun checkSuccess(url: String) = true

}