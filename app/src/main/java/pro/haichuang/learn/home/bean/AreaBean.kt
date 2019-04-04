package pro.haichuang.learn.home.bean

import android.view.View
import com.github.promeg.pinyinhelper.Pinyin
import com.google.gson.annotations.Expose
import pro.haichuang.learn.home.utils.mlog

class AreaBean {

    var city_code = ""
    var city_name = ""

    @Expose(serialize = false, deserialize = false)
    var pinyin = ""
        get() {
            if (field.isEmpty())
                field = Pinyin.toPinyin(city_name, "")
            return field
        }

    @Expose(serialize = false, deserialize = false)
    var showIcon = false

    @Expose(serialize = false, deserialize = false)
    var result: (city: String) -> Unit = {}

    fun click(v: View) {
        result(city_name)
    }
}