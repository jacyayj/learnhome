package pro.haichuang.learn.home.ui.activity.login

import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.login.viewmodel.RegisterModel
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_register)
class RegisterActivity : DataBindingActivity<RegisterModel>() {

    override fun initData() {
        titleModel.title = "注册"
        titleModel.titleLeftText = "登录"
        titleModel.showBottomeLine = false
    }

}
