package pro.haichuang.learn.home.ui.activity.index

import android.graphics.Color
import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.ContentView
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_school_compare.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_school_compare)
class SchoolCompareActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "计划对比"
        post(Url.College.Compare, HttpParams("ids", intent.getStringExtra(Constants.COMPARE_IDS)))
    }

    override fun onSuccess(url: String, result: Any?) {
        val array = GsonUtil.parseArray(result, CollegeModel::class.java)
        recyclerView.setCount(array.size)
        recyclerView.addItemDecoration(DefaultItemDecoration(Color.parseColor("#D7DDE0"), DensityUtil.dp2px(0.5f), 0))
        recyclerView.adapter = CommonRecyclerAdapter(layoutInflater, R.layout.item_school_compare, array)
    }

}
