package pro.haichuang.learn.home.utils

import android.content.Context
import com.google.gson.Gson
import com.jacy.kit.config.toJson
import com.netease.nimlib.sdk.auth.LoginInfo
import com.vondear.rxtool.RxTool

object SPUtils {

    private val sp by lazy { RxTool.getContext().getSharedPreferences("learn_home", Context.MODE_PRIVATE) }

    var session: String?
        get() = sp.getString("session", null)
        set(value) = sp.edit().putString("session", value).apply()

    var phone: String?
        get() = sp.getString("phone", null)
        set(value) = sp.edit().putString("phone", value).apply()

    var loginInfo: LoginInfo?
        get() = Gson().fromJson(sp.getString("loginInfo", null), LoginInfo::class.java)
        set(value) = sp.edit().putString("loginInfo", value?.toJson()).apply()

    fun clear() {
        sp.edit().clear().apply()
    }
}