package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_online_teacher.*
import kotlinx.android.synthetic.main.item_teacher.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.TEACHER_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemTeacherModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_online_teacher)
class TeacherActivity : BaseActivity() {

    private val online by lazy { intent.getBooleanExtra("online", false) }

    private val adapter by lazy {
        CommonAdapter<ItemTeacherModel>(layoutInflater, R.layout.item_teacher) { v, t, _ ->
            v.to_teacher.setOnClickListener {
                mStartActivity(TeacherDetailsActivity::class.java, Pair("online", online), Pair(TEACHER_ID, t.id))
            }
        }
    }

    override fun initData() {
        titleModel.title = if (online) "名师在线" else "心理舒压"
        listView.adapter = adapter
        pageUrl = Url.Teacher.List
        fetchPageData()
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("teacherType", if (online) "1" else "2")
    }

    override fun onSuccess(url: String, result: Any?) {
        val rows = GsonUtil.parseRows(result, ItemTeacherModel::class.java)
        rows.list?.let { dealRows(adapter, it) }
    }
}
