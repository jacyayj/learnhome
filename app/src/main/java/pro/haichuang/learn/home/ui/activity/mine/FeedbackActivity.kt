package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_feedback.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url


@ContentView(R.layout.activity_feedback)
class FeedbackActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "意见反馈"
    }

    override fun initListener() {
        send.setOnClickListener {
            if (input.text.isEmpty())
                toast("请输入反馈内容")
            else
                post(Url.Guestbook.Save, HttpParams("content",input.text.toString()),needSession = true){
                    toast("发送成功")
                    finish()
                }
        }
    }

}
