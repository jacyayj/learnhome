package pro.haichuang.learn.home.ui.activity.login

import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.zhouyou.http.EasyHttp
import kotlinx.android.synthetic.main.activity_register.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.MyCallBack
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
        fetch_sms.setOnClickListener {
            toast("开始发送")
            EasyHttp.post(Url.Sms.Send)
                    .params("mobile ", "18384124448")
                    .params("sendType", "1")
                    .execute(MyCallBack<String>(this))
        }
    }

    override fun onSuccess(result: Any?) {
        toast("发送成功")
    }
}
