package pro.haichuang.learn.home.ui.fragment.index

import android.app.Activity
import android.content.Intent
import android.view.View
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.mStartActivityForResult
import kotlinx.android.synthetic.main.item_zhiyuan_details.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.ui.activity.index.ZhiYuanSchoolActivity
import pro.haichuang.learn.home.ui.activity.index.ZhiYuanZhuanYeActivity
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.dialog.NoticeDialog
import pro.haichuang.learn.home.ui.dialog.ZhiYuanPopup

open class ZhiYuanFragment : BaseFragment() {

    protected val subject by lazy { arguments?.getInt(Constants.JUDGE_SUBJECT) ?: -1 }
    protected val mScore by lazy { arguments?.getInt(Constants.JUDGE_SCORE) ?: -1 }
    protected val batch by lazy { arguments?.getInt(Constants.JUDGE_BATCH) ?: -1 }
    private var temp: CollegeModel? = null

    private val popup by lazy {
        ZhiYuanPopup(layoutInflater) { zhiyuan ->
            adapter.data.find { it.zhiyuan == zhiyuan }?.let {
                NoticeDialog(context) {
                    temp?.zhiyuan = zhiyuan
                    temp?.checked = true
                    it.zhiyuan = "填报为"
                    it.checked = false
                }.show("提示", "${zhiyuan}当前选择的是${it.collegeName},是否替换？")
            } ?: let {
                temp?.zhiyuan = zhiyuan
                temp?.checked = true
            }
            temp?.choosed = false
        }
    }

    protected val adapter by lazy {
        CommonAdapter<CollegeModel>(layoutInflater, R.layout.item_zhiyuan_details) { v, t, _ ->
            v.to_choose_zhuanye.setOnClickListener {
                temp = t
                activity?.mStartActivityForResult(ZhiYuanZhuanYeActivity::class.java, 0x01,
                        Pair(Constants.COLLEGE_ID, t.id),
                        Pair("majorIds", t.majorIds),
                        Pair(Constants.JUDGE_SUBJECT, subject))
            }
            v.to_details.setOnClickListener {
                mStartActivity(ZhiYuanSchoolActivity::class.java)
            }
        }
    }

    fun reset(id: Int) {
        adapter.data.find { it.id == id }?.let {
            it.checked = false
            it.zhiyuan = "填报为"
        }
    }

    fun getChooseData(): ArrayList<CollegeModel> {
        return adapter.data.filter {  it.zhiyuan != "填报为"  } as ArrayList<CollegeModel>
    }

    fun choose(view: View) {
        val temp = view.tag
        if (temp is CollegeModel) {
            temp.choosed = true
            this.temp = temp
            popup.show(view, temp.zhiyuan)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                temp?.majorIds = it.getStringExtra("majorIds")
            }
        }
    }
}