package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_data_search.*
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_data_search)
class DataSearchActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "自主查询"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_note_book, arrayListOf("招生计划",
                "高校查询", "专业查询", "高考成绩查询", "高考录取查询", "普招提前录取"))
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> mStartActivity(ZhaoShengPlanActivity::class.java)
                1 -> mStartActivity(HeightSchoolSearchActivity::class.java)
                2 -> mStartActivity(ZhuanYeSearchActivity::class.java)
            }
        }
    }
}
