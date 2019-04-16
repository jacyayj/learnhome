package pro.haichuang.learn.home.ui.activity.index

import android.annotation.SuppressLint
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhiyuan_zhuanye.*
import kotlinx.android.synthetic.main.item_zhiyuan_zhuanye.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.COLLEGE_ID
import pro.haichuang.learn.home.config.Constants.JUDGE_SUBJECT
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.MajorModel
import pro.haichuang.learn.home.ui.dialog.LegendDialog
import pro.haichuang.learn.home.utils.GsonUtil


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
        post(Url.College.EnrollMajor, HttpParams("collegeId", intent.getIntExtra(COLLEGE_ID, -1).toString()).apply {
            put("subject", intent.getIntExtra(JUDGE_SUBJECT, -1).toString())
        })
    }

    override fun onSuccess(url: String, result: Any?) {
        val data = GsonUtil.parseArray(result, MajorModel::class.java)
        if (data.isEmpty())
            toast("暂无专业数据")
        else
            listView.adapter = CommonAdapter(layoutInflater, R.layout.item_zhiyuan_zhuanye, data) { v, _, _ ->
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
