package pro.haichuang.learn.home.ui.activity.mine.itemmodel

class PayModel {

    var id = 0
    var orderType = 0
    var orderAmount = ""
    var payTime = ""
    var payType = 0

    val payTypeStr
        get() = when(payType){
            12 -> "微信支付"
            13 -> "支付宝支付"
            else-> "钱包支付"
        }
}