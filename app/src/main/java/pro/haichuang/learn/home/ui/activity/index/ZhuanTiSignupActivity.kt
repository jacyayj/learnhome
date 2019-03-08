package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_zhuanti_signup.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.viewmodel.ZhuanTiSignupModel
import pro.haichuang.learn.home.ui.dialog.ZhuanTiDialog


@ContentView(R.layout.activity_zhuanti_signup)
class ZhuanTiSignupActivity : DataBindingActivity<ZhuanTiSignupModel>() {

    override fun initData() {
        titleModel.title = "报名信息"
        model.id = intent.getIntExtra(Constants.ZHUANTI_ID, -1).toString()
    }


    override fun initListener() {
        confirm.setOnClickListener {
            autoPost(Url.Lecture.Apply)
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        ZhuanTiDialog(this).apply {
            setOnDismissListener {
                finish()
            }
        }.show()
    }
}
