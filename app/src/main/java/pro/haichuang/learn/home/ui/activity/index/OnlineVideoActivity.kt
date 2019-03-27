package pro.haichuang.learn.home.ui.activity.index

import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_online_video.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.VIDEO_URL
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.VideoModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_online_video)
class OnlineVideoActivity : BaseActivity() {

    private val adapter by lazy { CommonAdapter<VideoModel>(layoutInflater, R.layout.item_online_video) }

    private var recommend = true

    private var videoType = ""

    override fun initData() {
        listView.adapter = adapter
        pageUrl = Url.Video.List
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
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(VideoPlayActivity::class.java, Pair(VIDEO_URL, adapter.getItem(position).attr?.videoUrl))
        }
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
                fetchPageData()
            }
        })
    }

    override fun onSuccess(url: String, result: Any?) {
        GsonUtil.parseRows(result, VideoModel::class.java).list?.let {
            dealRows(adapter, it)
        }
    }
}
