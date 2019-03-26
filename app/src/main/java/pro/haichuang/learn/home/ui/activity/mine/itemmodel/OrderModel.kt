package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.bean.Teacher

class OrderModel : BaseObservable() {
    var id = 0
    var orderStatus = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.text)
            notifyPropertyChanged(BR.canClick)
        }
    var isComment = false
    var refundState = false
    var orderAmount = ""
    var orderNumber = ""
    var teacherInfo: Teacher? = null

    @Bindable
    var canClick = true
        get() = when (orderStatus) {
            -1, 4 -> false
            0, 1 -> true
            2, 3 -> !isComment
            else -> false
        }

    @Bindable
    var text = ""
        get() = when (orderStatus) {
            -1 -> "交易关闭"
            0 -> "去付款"
            1 -> "退款"
            2, 3 -> if (isComment) "已完成" else "评价"
            4 -> if (refundState) "已退款" else "退款中"
            else -> ""
        }

}