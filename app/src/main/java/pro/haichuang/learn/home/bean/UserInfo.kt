package pro.haichuang.learn.home.bean

import com.google.gson.annotations.SerializedName

class UserInfo {
    var id = 0
    var totalAttention = 0
    var totalComment = 0
    var totalFans = 0
    var totalPublish = 0
    var city = ""
    var teachername = ""
    var realname = ""
    var teacherImg = ""
    var userImg = ""
    var sessionKey = ""
    var imToken = ""
    var imAccid = ""
    @SerializedName("isVip",alternate = ["vip"])
    var isVip = false
    var teacher = false
    var hasPayPassword = false
}