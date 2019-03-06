package pro.haichuang.learn.home.utils

import android.content.Context
import com.vondear.rxtool.RxTool

object SPUtils {

    private val sp by lazy { RxTool.getContext().getSharedPreferences("learn_home", Context.MODE_PRIVATE) }

    var session: String?
        get() = sp.getString("session", null)
        set(value) = sp.edit().putString("session", value).apply()
}