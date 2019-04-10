package pro.haichuang.learn.home.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import com.github.promeg.pinyinhelper.Pinyin
import com.google.gson.annotations.Expose
import pro.haichuang.learn.home.utils.mlog

class AreaBean:BaseObservable() {

    var city_code = ""
    @Bindable
    var city_name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.city_name)
        }
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