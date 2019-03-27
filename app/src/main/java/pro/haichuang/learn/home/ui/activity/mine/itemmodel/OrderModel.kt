package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.bean.Author
import pro.haichuang.learn.home.bean.Teacher

class OrderModel : BaseObservable() {
    var id = 0
    var orderType = 0
    var orderStatus = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.text)
            notifyPropertyChanged(BR.teacherText)
            notifyPropertyChanged(BR.canClick)
            notifyPropertyChanged(BR.teacherCanClick)
        }
    var isComment = false
    var refundState = false
    var orderAmount = ""
    var orderNumber = ""
    var createTime = ""
    var teacherInfo: Teacher? = null
    var memberInfo: Author? = null

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

    @Bindable
    var teacherCanClick = true
        get() = when (orderStatus) {
            1, 2 -> true
            else -> false
        }

    @Bindable
    var teacherText = ""
        get() = when (orderStatus) {
            0 -> "待付款"
            1 -> if (orderType == 1) "联系" else "完成"
            2 -> "联系"
            3 -> "已完成"
            4 -> if (refundState) "已退款" else "退款中"
            else -> "交易关闭"
        }

}