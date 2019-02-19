package pro.haichuang.learn.home.ui.activity.index

import kotlinx.android.synthetic.main.activity_zhuanti_signup.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.dialog.ZhuanTiDialog


@ContentView(R.layout.activity_zhuanti_signup)
class ZhuanTiSignupActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "报名信息"
    }


    override fun initListener() {
        confirm.setOnClickListener { ZhuanTiDialog(this).show() }
    }
}
