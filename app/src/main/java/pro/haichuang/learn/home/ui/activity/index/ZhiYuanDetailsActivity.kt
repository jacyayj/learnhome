package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.gone
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.activity_zhiyuan_details.*
import kotlinx.android.synthetic.main.item_zhiyuan_details.view.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ZhiYuanModel
import pro.haichuang.learn.home.ui.dialog.TitleNoticeDialog
import pro.haichuang.learn.home.ui.dialog.ZhiYuanResultDialog


@ContentView(R.layout.activity_zhiyuan_details)
class ZhiYuanDetailsActivity : BaseActivity() {
    private val tabBeans by lazy { arrayListOf(TabBean("四川省"), TabBean("院校类型")) }
    private val data by lazy {
        arrayListOf(ZhiYuanModel(), ZhiYuanModel(),
                ZhiYuanModel(), ZhiYuanModel(), ZhiYuanModel(), ZhiYuanModel(), ZhiYuanModel(), ZhiYuanModel())
    }
    private val zhiyuanDialog by lazy {
        ZhiYuanResultDialog(this) {
            mStartActivity(ZhiYuanResultActivity::class.java)
        }
    }
    private val adapter by lazy {
        CommonAdapter(layoutInflater, R.layout.item_zhiyuan_details, data) { v, _, _ ->
            v.to_choose_zhuanye.setOnClickListener {
                mStartActivity(ZhiYuanZhuanYeActivity::class.java)
            }
            v.to_details.setOnClickListener {
                mStartActivity(ZhiYuanSchoolActivity::class.java)
            }
        }
    }
    private val adapter2 by lazy { CommonAdapter(layoutInflater, R.layout.item_zhiyuan_details2, arrayListOf(1, 2, 3, 4, 5, 6)) }
    override fun initData() {
        titleModel.title = "成绩:610分 文科   线差:57   本一批"
        initTab()
        listView.adapter = adapter
    }

    override fun initListener() {
        create_zhiyuan.setOnClickListener {
            TitleNoticeDialog(this).show()
        }
        show_result.setOnClickListener {
            zhiyuanDialog.show()
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        listView.adapter = adapter
                        search_view.gone()
                        sub_tab.show()
                        data.forEach {
                            it.zizhu = false
                        }
                    }
                    1 -> {
                        listView.adapter = adapter2
                        search_view.show()
                        sub_tab.gone()
                    }
                    2 -> {
                        listView.adapter = adapter
                        search_view.gone()
                        sub_tab.show()
                        data.forEach {
                            it.zizhu = true
                        }
                    }
                }
            }
        })
    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_17, sub_tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            sub_tab.addTab(sub_tab.newTab().setCustomView(binding.root))
        }
    }
}
