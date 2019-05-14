package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import kotlinx.android.synthetic.main.activity_liu_xue_zi_xun.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.viewmodel.LiuXueZiXunModel


@ContentView(R.layout.activity_liu_xue_zi_xun)
class LiuXueZiXunActivity : DataBindingActivity<LiuXueZiXunModel>() {

    override fun initData() {
        titleModel.title = "免费咨询"
    }

    override fun initListener() {
        confirm.setOnClickListener {
            autoPost(Url.Consult.Save)
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        toast("提交成功")
        finish()
    }
}
