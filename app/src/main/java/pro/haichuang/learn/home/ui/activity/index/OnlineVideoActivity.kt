package pro.haichuang.learn.home.ui.activity.index

import android.support.design.widget.TabLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.show
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_online_video.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.SearchAdapter
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.VIDEO_URL
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.VideoModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_online_video)
class OnlineVideoActivity : BaseActivity() {

    private val adapter by lazy { SearchAdapter(layoutInflater, R.layout.item_online_video, data) }

    private val data by lazy { ArrayList<VideoModel>() }

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
        clear.setEdit(search_input)
        search_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    clear.gone()
                    adapter.doSearch("")
                } else {
                    clear.show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        search_input.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                adapter.doSearch(search_input.text.toString())
                true
            } else {
                false
            }
        }
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
        when (url) {
            Url.Video.List -> {
                GsonUtil.parseRows(result, VideoModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }

    }
}
