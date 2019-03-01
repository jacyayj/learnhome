package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_teacher_details.*
import kotlinx.android.synthetic.main.item_find_details_comment.view.*
import com.jacy.kit.config.ContentView
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.index.viewmodel.TeacherDetailsModel
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.RowsBean
import pro.haichuang.learn.home.config.Constants.TEACHER_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.PersonalIndexActivity
import pro.haichuang.learn.home.ui.activity.find.YuYueActivity
import pro.haichuang.learn.home.ui.activity.find.itemmodel.CommentModel
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemTeacherModel
import pro.haichuang.learn.home.ui.dialog.PaymentDialog


@ContentView(R.layout.activity_teacher_details)
class TeacherDetailsActivity : DataBindingActivity<TeacherDetailsModel>() {

    private val id by lazy { intent.getIntExtra(TEACHER_ID,-1) }

    override fun initData() {
        model.online = intent.getBooleanExtra("online", false)
        titleModel.title = if (model.online) "名师在线" else "老师详情"
        comment.adapter = CommonRecyclerAdapter(layoutInflater, R.layout.item_find_details_comment, arrayListOf(1, 2, 3, 4, 5, 6, 7)) { v, _, _ ->
            v.to_index.setOnClickListener {
                mStartActivity(PersonalIndexActivity::class.java)
            }
            v.comment_child.adapter = CommonRecyclerAdapter(layoutInflater,
                    R.layout.item_find_details_comment_child,
                    arrayListOf(CommentModel(), CommentModel(), CommentModel(), CommentModel()))
        }

        val params = HttpParams()
        params.put("id", id.toString())
        post<String>(Url.Teacher.Get, params)
    }

    override fun onSuccess(url: String, result: Any?) {

    }
    override fun initListener() {
        to_yuyue.setOnClickListener { mStartActivity(YuYueActivity::class.java) }
        off_online.setOnClickListener { mStartActivity(YuYueActivity::class.java) }
        online.setOnClickListener {
            PaymentDialog(this).show()
        }
    }
}
