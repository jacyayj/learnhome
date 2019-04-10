package pro.haichuang.learn.home.ui.activity.index

import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.show
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhao_sheng_school_details.*
import kotlinx.android.synthetic.main.layout_jianzhang.*
import kotlinx.android.synthetic.main.layout_school_details_intro.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.MajorModel
import pro.haichuang.learn.home.ui.activity.index.viewmodel.HeightSchoolDetailsModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_zhao_sheng_school_details)
class ZhaoShengSchoolDetailsActivity : DataBindingActivity<HeightSchoolDetailsModel>() {

    override fun initData() {
        titleModel.title = "高校详情"
        model.id = intent.getIntExtra(Constants.SCHOOL_ID, -1)
        post(Url.College.Get, HttpParams("id", model.id.toString()))
        post(Url.College.EnrollMajor, HttpParams("collegeId", model.id.toString()))
        jianzhang_view.settings.textZoom = 250
    }

    override fun initListener() {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        layout_school_plan.show()
                        layout_school_intro.gone()
                        jianzhang_view.gone()
                    }
                    1 -> {
                        layout_school_plan.gone()
                        layout_school_intro.show()
                        jianzhang_view.gone()
                    }
                    2 -> {
                        layout_school_plan.gone()
                        layout_school_intro.gone()
                        jianzhang_view.show()
                    }
                }
            }
        })
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.College.Get -> notifyModel(GsonUtil.parseObject(result, HeightSchoolDetailsModel::class.java))
            Url.College.EnrollMajor -> listView.adapter = CommonAdapter(layoutInflater, R.layout.item_zhao_sheng_school_major, GsonUtil.parseArray(result, MajorModel::class.java))
        }
    }
}
