package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.config.ContentView
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_help.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.QUESTION_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_help)
class HelpActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "用户帮助"
        post(Url.Fqa.Get, HttpParams().apply {
            put("id", intent.getIntExtra(QUESTION_ID, -1).toString())
        })
    }

    override fun onSuccess(url: String, result: Any?) {
        question_tv.text = GsonUtil.getString(result,"question")
        answer_tv.text = GsonUtil.getString(result,"answer")
    }

}
