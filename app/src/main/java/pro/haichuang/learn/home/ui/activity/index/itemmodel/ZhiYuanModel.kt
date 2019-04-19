package pro.haichuang.learn.home.ui.activity.index.itemmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.ui.dialog.ZhiYuanPopup

class ZhiYuanModel : BaseModel() {

    private var popup: ZhiYuanPopup? = null

    @Bindable
    var zhiyuan = "填报为"
        set(value) {
            field = value
            notifyPropertyChanged(BR.zhiyuan)
        }
    @Bindable
    var choosed = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.choosed)
        }

    @Bindable
    var checked = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
        }

    @Bindable
    var zizhu = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.zizhu)
        }


}