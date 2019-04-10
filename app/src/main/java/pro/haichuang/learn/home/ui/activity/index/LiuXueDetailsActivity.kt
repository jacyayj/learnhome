package pro.haichuang.learn.home.ui.activity.index

import android.view.View
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.jacy.kit.config.ContentView
import com.yhy.widget.layout.flow.tag.TagFlowAdapter
import com.yhy.widget.layout.flow.tag.TagFlowLayout
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_liu_xue_details.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants.COLLEGE_ID
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.viewmodel.LiuXueDetailsModel
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.mlog


@ContentView(R.layout.activity_liu_xue_details)
class LiuXueDetailsActivity : DataBindingActivity<LiuXueDetailsModel>() {

    override fun initData() {
        val params = HttpParams()
        params.put("id", intent.getIntExtra(COLLEGE_ID, -1).toString())
        post(Url.ForeignCollege.Get, params)
    }

    override fun onSuccess(url: String, result: Any?) {
        mlog.v(GsonBuilder().setPrettyPrinting().create().toJson(result))
        notifyModel(GsonUtil.parseObject(result, LiuXueDetailsModel::class.java))
        intro_view.loadData(model.intro)
        intro_view.settings.textZoom = 250
        (tag as TagFlowLayout<String>).setAdapter(object : TagFlowAdapter<String>(model.hotMajor.split(" ")) {
            override fun getView(parent: TagFlowLayout<*>?, position: Int, data: String?): View {
                val view = layoutInflater.inflate(R.layout.item_tag, parent, false)
                (view as TextView).text = data
                return view
            }
        })
    }
}
