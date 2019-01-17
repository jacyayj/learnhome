package pro.haichuang.learn.home.ui.activity.login

import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.login.viewmodel.ForgetPwdModel
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_modify_pwd)
class ModifyPwdActivity : DataBindingActivity<ForgetPwdModel>() {

    override fun initData() {
        titleModel.title = "忘记密码"
        titleModel.titleLeftText = "登录"
        titleModel.showBottomeLine = false
    }

}
