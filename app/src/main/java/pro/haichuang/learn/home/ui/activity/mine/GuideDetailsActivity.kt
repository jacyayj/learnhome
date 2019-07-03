package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.config.ContentView
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_guide_details.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.GUIDE_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.activity_guide_details)
class GuideDetailsActivity : BaseActivity() {

    override fun initData() {
        post(Url.Guide.Get, HttpParams("id", intent.getIntExtra(GUIDE_ID, -1).toString()))
        content.settings.textZoom = 250
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Guide.Get -> {
                content.loadData(GsonUtil.getString(result,"content"))
                titleModel.title = GsonUtil.getString(result,"title")
            }
        }
    }
}
