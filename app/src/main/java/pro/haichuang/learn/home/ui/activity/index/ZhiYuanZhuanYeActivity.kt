package pro.haichuang.learn.home.ui.activity.index

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhiyuan_zhuanye.*
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

    private lateinit var data: ArrayList<MajorModel>

    @SuppressLint("SetTextI18n")
    override fun initData() {
        titleModel.title = "选择专业"
        titleModel.titleRightIcon = R.drawable.icon_notice
        titleModel.titleRightText = "专业概率"
        titleModel.showRight = true
        titleModel.onRightClick = {
            LegendDialog(this).show(4)
        }
        post(Url.College.EnrollMajor, HttpParams("collegeId", intent.getIntExtra(COLLEGE_ID, -1).toString()).apply {
            put("subject", intent.getIntExtra(JUDGE_SUBJECT, -1).toString())
        })
    }

    override fun onSuccess(url: String, result: Any?) {
        data = GsonUtil.parseArray(result, MajorModel::class.java)
        if (data.isEmpty())
            toast("暂无专业数据")
        else
            listView.adapter = CommonAdapter(layoutInflater, R.layout.item_zhiyuan_zhuanye, data)
    }

    override fun onBackPressed() {
        if (::data.isInitialized) {
            var majorIds = ""
            data.filter { it.checked }.forEach {
                majorIds += it.id.toString() + ","
            }
            if (majorIds.isNotEmpty())
                setResult(Activity.RESULT_OK, Intent().putExtra("majorIds", majorIds.removeSuffix(",")))
        }
        finish()
    }

}
