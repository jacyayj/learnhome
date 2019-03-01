package pro.haichuang.learn.home.ui.activity.login

import com.google.gson.Gson
import com.jacy.kit.config.ContentView
import com.zhouyou.http.EasyHttp
import kotlinx.android.synthetic.main.activity_register.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.login.viewmodel.RegisterModel
import pro.haichuang.learn.home.utils.mlog

@ContentView(R.layout.activity_register)
class RegisterActivity : DataBindingActivity<RegisterModel>() {

    override fun initData() {
        titleModel.title = "注册"
        titleModel.titleLeftText = "登录"
        titleModel.showBottomeLine = false
    }

    override fun initListener() {
        fetch_sms.setOnClickListener {
            autoPost<String>(Url.Sms.Send)
        }
        register.setOnClickListener {
            autoPost<String>(Url.User.Register)
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Sms.Send -> model.code = result.toString()
            Url.User.Register -> finish()
        }
    }
}
