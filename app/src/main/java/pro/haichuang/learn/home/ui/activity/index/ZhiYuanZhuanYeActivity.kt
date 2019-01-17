package pro.haichuang.learn.home.ui.activity.index

import android.annotation.SuppressLint
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_zhiyuan_zhuanye.*
import kotlinx.android.synthetic.main.item_zhiyuan_zhuanye.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.dialog.LegendDialog


@ContentView(R.layout.activity_zhiyuan_zhuanye)
class ZhiYuanZhuanYeActivity : BaseActivity() {

    private var chooseCount = 0

    @SuppressLint("SetTextI18n")
    override fun initData() {
        titleModel.title = "选择专业"
        titleModel.titleRightIcon = R.drawable.icon_notice
        titleModel.titleRightText = "专业概率"
        titleModel.showRight = true
        titleModel.onRightClick = {
            LegendDialog(this).show(3)
        }
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_zhiyuan_zhuanye, arrayListOf(1, 2, 3, 4, 5, 6)) { v, _, _ ->
            v.choose_zhuanye.setOnClickListener {
                if (v.choose_zhuanye.isChecked) {
                    chooseCount++
                    v.choose_zhuanye.text = "已选专业$chooseCount"
                } else {
                    chooseCount--
                    v.choose_zhuanye.text = "选择专业"
                }
            }
        }
    }
}
