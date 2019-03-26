package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.config.ContentView
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.COLLEGE_ID
import pro.haichuang.learn.home.net.Url


@ContentView(R.layout.activity_liu_xue_details)
class LiuXueDetailsActivity : BaseActivity() {

    override fun initData() {
        val params = HttpParams()
        params.put("id", intent.getIntExtra(COLLEGE_ID, -1).toString())
        post(Url.ForeignCollege.Get, params)
    }

}
