package pro.haichuang.learn.home.ui.activity.index.itemmodel

import pro.haichuang.learn.home.bean.BaseModel
import android.databinding.Bindable
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.mStartActivity
import pro.haichuang.learn.home.ui.activity.index.SchoolDetailsActivity

class ItemCompareModel : BaseModel() {

    @Bindable
    var compare = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.compare)
        }

    @Bindable
    var checked = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
        }

    fun toggleChecked(view: View) {
        if (compare)
            checked = checked.not()
        else
            view.context?.mStartActivity(SchoolDetailsActivity::class.java)
    }
}