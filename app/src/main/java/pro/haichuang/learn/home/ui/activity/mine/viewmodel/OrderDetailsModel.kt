package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.net.Params
import pro.haichuang.learn.home.bean.Author
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.bean.Teacher
import pro.haichuang.learn.home.net.Url

class OrderDetailsModel : BaseModel() {


    @Params([Url.Order.Get, Url.Order.Pay, Url.Order.Cancel], "orderId")
    var id = 0
    var orderStatus = 0
    var orderType = 0
    @Params([Url.Order.Pay], "payType")
    var payType = 0
    var acceptTime = ""
    var appointTime = ""
    var cancelTime = ""
    var createTime = ""
    var finishTime = ""
    var payTime = ""
    var contactName = ""
    var contactPhone = ""
    var orderAmount = ""
    var pictures = ""
    var question = ""
    var isComment = false
    var refundState = false
    var memberInfo: Author? = null
    var teacherInfo: Teacher? = null

    @Bindable
    var type = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }

    val canOperation
        get() = orderStatus == 0

    val canComment
        get() = (orderStatus == 2 || orderStatus == 3) && !isComment

    val text
        get() = when (orderStatus) {
            -1 -> "交易关闭"
            1 -> "已付款"
            2, 3 -> if (isComment) "已完成" else "待评价"
            4 -> if (refundState) "已退款" else "退款中"
            else -> ""
        }
}