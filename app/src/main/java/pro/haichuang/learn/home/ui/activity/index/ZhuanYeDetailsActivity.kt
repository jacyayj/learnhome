package pro.haichuang.learn.home.ui.activity.index

import android.support.design.widget.TabLayout
import com.google.gson.GsonBuilder
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.show
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhuan_ye_details.*
import kotlinx.android.synthetic.main.layout_zhuanye_details_tab_1.*
import kotlinx.android.synthetic.main.layout_zhuanye_details_tab_2.*
import kotlinx.android.synthetic.main.layout_zhuanye_details_tab_3.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants.ZHUAN_YE_ID
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemZuanYeModel
import pro.haichuang.learn.home.ui.activity.index.viewmodel.ZhuanYeDeailsModel
import pro.haichuang.learn.home.ui.dialog.LegendDialog
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.mlog


@ContentView(R.layout.activity_zhuan_ye_details)
class ZhuanYeDetailsActivity : DataBindingActivity<ZhuanYeDeailsModel>() {

    override fun initData() {
        titleModel.title = "专业信息"
        post(Url.Major.Get, HttpParams("id", intent.getIntExtra(ZHUAN_YE_ID, -1).toString()))
    }

    override fun initListener() {
        legend.setOnClickListener {
            LegendDialog(this).show(0)
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        zhuanye_details_tab1.show()
                        zhuanye_details_tab2.gone()
                        zhuanye_details_tab3.gone()
                    }
                    1 -> {
                        zhuanye_details_tab1.gone()
                        zhuanye_details_tab2.show()
                        zhuanye_details_tab3.gone()
                    }
                    2 -> {
                        zhuanye_details_tab1.gone()
                        zhuanye_details_tab2.gone()
                        zhuanye_details_tab3.show()
                    }
                }
            }
        })
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Major.College -> {
                listView.adapter = CommonAdapter(layoutInflater, R.layout.item_height_school_details_3, GsonUtil.parseArray(result, ItemZuanYeModel::class.java))
            }
            Url.Major.Get -> {
                notifyModel(GsonUtil.parseObject(result, ZhuanYeDeailsModel::class.java))
                mlog.v(GsonBuilder().setPrettyPrinting().create().toJson(result))
//                intro_view.loadData(model.intro)
                post(Url.Major.College, HttpParams("majorName", model.majorName))
            }
        }
    }
}
