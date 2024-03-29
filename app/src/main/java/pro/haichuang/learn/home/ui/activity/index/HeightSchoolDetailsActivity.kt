package pro.haichuang.learn.home.ui.activity.index

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.net.Uri
import android.support.design.widget.TabLayout
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.show
import com.jacy.kit.config.toast
import com.luck.picture.lib.permissions.RxPermissions
import com.vondear.rxtool.RxDeviceTool
import com.vondear.rxtool.RxPermissionsTool
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_height_school_details.*
import kotlinx.android.synthetic.main.layout_jianzhang.*
import kotlinx.android.synthetic.main.layout_school_details_intro.*
import kotlinx.android.synthetic.main.layout_school_details_tabchildview_1.*
import kotlinx.android.synthetic.main.layout_school_details_tabchildview_2.*
import kotlinx.android.synthetic.main.layout_school_details_tabchildview_3.*
import kotlinx.android.synthetic.main.layout_school_details_tabchildview_4.*
import kotlinx.android.synthetic.main.layout_school_details_tabchildview_5.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.viewmodel.HeightSchoolDetailsModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_height_school_details)
class HeightSchoolDetailsActivity : DataBindingActivity<HeightSchoolDetailsModel>() {
    private val childTabs by lazy { arrayListOf(child_tab_view1, child_tab_view2, child_tab_view3, child_tab_view4, child_tab_view5) }
    private val tabBeans by lazy { arrayListOf(TabBean("高校简介")/*, TabBean(true, "求学价值"), TabBean(true, "主要专业")*/, TabBean("招生章程")) }
    override fun initData() {
        titleModel.title = "高校详情"
        initTab()
//        zuanye_listview.adapter = CommonAdapter(layoutInflater, R.layout.item_height_school_details_3, arrayListOf(ItemZuanYeModel("社会工作"),
//                ItemZuanYeModel("社会工作"),
//                ItemZuanYeModel("应用语言学"),
//                ItemZuanYeModel("阿拉伯语"),
//                ItemZuanYeModel("波斯语"),
//                ItemZuanYeModel("印度尼西亚语"),
//                ItemZuanYeModel("印地语"),
//                ItemZuanYeModel("缅甸语"),
//                ItemZuanYeModel("蒙古语"),
//                ItemZuanYeModel("乌尔都语"),
//                ItemZuanYeModel("考古学")))
        post(Url.College.Get, HttpParams().apply {
            put("id", intent.getIntExtra(Constants.SCHOOL_ID, -1).toString())
        }, needSession = true)
        jianzhang_view.settings.textZoom = 200
    }

    override fun initListener() {
//        legend.setOnClickListener {
//            LegendDialog(this).show(0)
//        }
        website.setOnClickListener {
            val intent = Intent("android.intent.action.VIEW")

            if (!model.website.startsWith("http"))
                intent.data = Uri.parse("http://${model.website}")
            else
                intent.data = Uri.parse(model.website)
            startActivity(intent)
        }

        phone.setOnClickListener {
            if (RxPermissions(this).isGranted(Manifest.permission.CALL_PHONE)) {
                RxDeviceTool.callPhone(this, model.contact)
            } else {
                RxPermissionsTool.with(this).addPermission(Manifest.permission.CALL_PHONE).initPermission()
            }
        }
        collect.setOnClickListener {
            post(Url.College.Collect, HttpParams("collegeId", model.id.toString()).apply {
                put("operate", if (model.hasCollect) "0" else "1")
            }, needSession = true) {
                toast(if (model.hasCollect) "取消收藏成功" else "收藏成功")
                model.hasCollect = model.hasCollect.not()
            }
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        layout_school_intro.show()
//                        tab_view2.gone()
//                        tab_view3.gone()
                        jianzhang_view.gone()
                    }
//                    1 -> {
//                        tab_view1.gone()
//                        tab_view2.show()
//                        tab_view3.gone()
//                        jianzhang_view.gone()
//                    }
//                    2 -> {
//                        tab_view1.gone()
//                        tab_view2.gone()
//                        tab_view3.show()
//                        jianzhang_view.gone()
//                    }
                    1 -> {
                        layout_school_intro.gone()
//                        tab_view2.gone()
//                        tab_view3.gone()
                        jianzhang_view.show()
                    }
                }
            }
        })
//        child_tab.setOnCheckedChangeListener { _, checkedId ->
//            when (checkedId) {
//                R.id.tab1 -> toggleChild(0)
//                R.id.tab2 -> toggleChild(1)
//                R.id.tab3 -> toggleChild(2)
//                R.id.tab4 -> toggleChild(3)
//                R.id.tab5 -> toggleChild(4)
//            }
//        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            RxDeviceTool.callPhone(this, model.contact)
    }

    private fun toggleChild(position: Int) {
        childTabs.forEachIndexed { index, it ->
            if (index == position)
                it.show()
            else
                it.gone()
        }
    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_vip, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.College.Get -> notifyModel(GsonUtil.parseObject(result, HeightSchoolDetailsModel::class.java))
        }
    }
}
