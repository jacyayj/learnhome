package pro.haichuang.learn.home.ui.activity.login

import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import kotlinx.android.synthetic.main.activity_register.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.login.viewmodel.RegisterModel

@ContentView(R.layout.activity_register)
class RegisterActivity : DataBindingActivity<RegisterModel>() {

    override fun initData() {
        titleModel.title = "注册"
        titleModel.titleLeftText = "登录"
        titleModel.showBottomeLine = false
    }

    override fun initListener() {
        clear.setEdit(phone)
        pwd_toggle.setEdit(pwd)
        confirm_pwd_toggle.setEdit(confirm_pwd)
        fetch_sms.requestCode = {
            autoPost(Url.Sms.Send)
        }
        register.setOnClickListener {
            autoPost(Url.User.Register)
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Sms.Send -> {
                fetch_sms.notifyCount()
                toast("发送成功")
            }
            Url.User.Register -> {
                toast("注册成功")
                finish()
            }
        }
    }
}
