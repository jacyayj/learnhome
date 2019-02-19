package pro.haichuang.learn.home.ui.activity.index

import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.gone
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.activity_zhuan_ye_details.*
import kotlinx.android.synthetic.main.layout_zhuanye_details_tab_1.*
import kotlinx.android.synthetic.main.layout_zhuanye_details_tab_2.*
import kotlinx.android.synthetic.main.layout_zhuanye_details_tab_3.*
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemZuanYeModel
import pro.haichuang.learn.home.ui.dialog.LegendDialog


@ContentView(R.layout.activity_zhuan_ye_details)
class ZhuanYeDetailsActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "专业信息"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_height_school_details_3, arrayListOf(ItemZuanYeModel("中国人民大学"),
                ItemZuanYeModel("北京大学"),
                ItemZuanYeModel("南京大学"),
                ItemZuanYeModel("武汉大学"),
                ItemZuanYeModel("复旦大学"),
                ItemZuanYeModel("中山大学"),
                ItemZuanYeModel("华东师范大学"),
                ItemZuanYeModel("清华大学"),
                ItemZuanYeModel("浙江大学"),
                ItemZuanYeModel("山西大学")))
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
}
