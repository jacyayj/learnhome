package pro.haichuang.learn.home.ui.activity.index

import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_online_video.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url


@ContentView(R.layout.activity_online_video)
class OnlineVideoActivity : BaseActivity() {

    private var recommend = true

    private var videoType = ""

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_online_video, arrayListOf(1, 2, 3, 4, 5, 6))
        pageUrl=Url.Video.List
        fetchPageData()
    }

    override fun setPageParams(pageParams: HttpParams) {
        if (videoType.isNotEmpty())
            pageParams.put("videoType", videoType)
        else
            pageParams.remove("videoType")
        pageParams.put("recommend", recommend.toString())
    }

    override fun initListener() {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        recommend = true
                        videoType = ""
                    }
                    1 -> {
                        recommend = false
                        videoType = "文科"
                    }
                    2 -> {
                        recommend = false
                        videoType = "理科"
                    }
                }
            }
        })
    }
}
