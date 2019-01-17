package pro.haichuang.learn.home.ui.activity.mine

import kotlinx.android.synthetic.main.activity_sett_pwd.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.SettPwdModel


@ContentView(R.layout.activity_sett_pwd)
class SettPwdActivity : DataBindingActivity<SettPwdModel>() {


    override fun initData() {
        titleModel.title = "设置密码"
    }

    override fun initListener() {
        pwd.setOnTextChangeListener { s, b ->
            if (b)
                when (model.step) {
                    0 -> {
                        model.originPwd = s
                        model.step++
                        pwd.setText("")
                    }
                    1 -> {
                        model.newPwd = s
                        model.step++
                        pwd.setText("")
                    }
                    2 -> {
                        model.confirmPwd = s
                    }
                }
        }
    }

    override fun onBackPressed() {
        if (model.step == 0)
            super.onBackPressed()
        else {
            pwd.setText("")
            model.step--
        }
    }
}
