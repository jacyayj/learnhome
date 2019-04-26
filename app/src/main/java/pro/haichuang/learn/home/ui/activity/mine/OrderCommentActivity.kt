package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_order_comment.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.TEACHER_ID
import pro.haichuang.learn.home.net.Url


@ContentView(R.layout.activity_order_comment)
class OrderCommentActivity : BaseActivity() {

    private val teacherId by lazy { intent.getStringExtra(TEACHER_ID) }

    override fun initData() {
        titleModel.title = "评价"
    }

    override fun initListener() {
        comment.setOnClickListener {
            val text = input.text.toString()
            if (text.isEmpty())
                toast("请输入评论内容")
            else
                post(Url.Teacher.CommentSave, HttpParams("teacherId", teacherId).apply {
                    put("text", text)
                }, needSession = true) {
                    toast("评论成功")
                    finish()
                }
        }
    }
}
