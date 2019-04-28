package pro.haichuang.learn.home.ui.activity.login

import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import kotlinx.android.synthetic.main.activity_modify_pwd.*
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.login.viewmodel.ForgetPwdModel
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.net.Url


@ContentView(R.layout.activity_modify_pwd)
class ModifyPwdActivity : DataBindingActivity<ForgetPwdModel>() {

    override fun initData() {
        titleModel.title = "忘记密码"
        titleModel.titleLeftText = "登录"
        titleModel.showBottomeLine = false
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.User.ForgetPassword -> {
                toast("修改成功")
                finish()
            }
            Url.Sms.Send -> {
                fetch_sms.notifyCount()
                toast("发送成功")
            }
        }
    }

    override fun initListener() {
        fetch_sms.requestCode = {
            autoPost(Url.Sms.Send)
        }
        confirm.setOnClickListener {
            autoPost(Url.User.ForgetPassword)
        }
        clear_phone.setOnClickListener {
            model.phone = ""
        }
        pwd_toggle.setEdit(pwd)
        confirm_pwd_toggle.setEdit(confirm_pwd)
    }
}
