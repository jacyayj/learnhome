package pro.haichuang.learn.home.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.google.gson.annotations.Expose
import java.io.Serializable

class AreaBean : BaseObservable(), Serializable {

    var city_code = ""

    var adcode = ""

    @Bindable
    var city_name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.city_name)
        }

    var pinyin = ""

    val child = ArrayList<AreaBean>()

    @Expose(serialize = false, deserialize = false)
    var showIcon = false
}