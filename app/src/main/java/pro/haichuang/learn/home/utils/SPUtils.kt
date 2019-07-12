package pro.haichuang.learn.home.utils

import android.content.Context
import com.google.gson.Gson
import com.jacy.kit.config.toJson
import com.netease.nimlib.sdk.auth.LoginInfo
import com.vondear.rxtool.RxTool
import pro.haichuang.learn.home.bean.UserInfo

object SPUtils {

    private val sp by lazy { RxTool.getContext().getSharedPreferences("learn_home", Context.MODE_PRIVATE) }

    var session: String?
        get() = sp.getString("session", null)
        private set(value) = sp.edit().putString("session", value).apply()

    var phone: String?
        get() = sp.getString("phone", null)
        set(value) = sp.edit().putString("phone", value).apply()

    var loginInfo: LoginInfo?
        get() = Gson().fromJson(sp.getString("loginInfo", null), LoginInfo::class.java)
        set(value) = sp.edit().putString("loginInfo", value?.toJson()).apply()

    var userInfo: UserInfo?
        get() = Gson().fromJson(sp.getString("userInfo", null), UserInfo::class.java)
        set(value) {
            session = value?.sessionKey
            isVip = value?.isVip ?: false
            isTeacher = value?.teacher ?: false
            sp.edit().putString("userInfo", value?.toJson()).apply()
        }

    var userName: String
        get() = sp.getString("userName", "") ?: ""
        set(value) = sp.edit().putString("userName", value).apply()

    var isVip: Boolean
        get() = sp.getBoolean("isVip", false)
        set(value) = sp.edit().putBoolean("isVip", value).apply()

    var isTeacher: Boolean
        get() = sp.getBoolean("isTeacher", false)
        private set(value) = sp.edit().putBoolean("isTeacher", value).apply()

    var isRegister: Boolean
        get() = sp.getBoolean("isRegister", false)
        set(value) = sp.edit().putBoolean("isRegister", value).apply()

    var isTourist: Boolean
        get() = sp.getBoolean("isTourist", false)
        set(value) = sp.edit().putBoolean("isTourist", value).apply()

    fun clear() {
        sp.edit().clear().apply()
    }
}