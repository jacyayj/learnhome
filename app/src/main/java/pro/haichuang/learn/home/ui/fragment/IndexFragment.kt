package pro.haichuang.learn.home.ui.fragment

import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.fragment_index.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.CityListActivity
import pro.haichuang.learn.home.ui.activity.find.FindDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.*
import pro.haichuang.learn.home.ui.dialog.IndexOperationPopup
import pro.haichuang.learn.home.ui.fragment.itemview.ItemNews
import pro.haichuang.learn.home.utils.DataUtils
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.mlog

@ContentView(R.layout.fragment_index)
class IndexFragment : BaseFragment() {

    private val adapter by lazy { CommonAdapter<ItemNews>(layoutInflater, R.layout.item_index_list) }

    private val locationOption by lazy {
        AMapLocationClientOption().apply {
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            interval = 2000
        }
    }

    private val locationClient by lazy {
        AMapLocationClient(context).apply {
            setLocationOption(locationOption)
            setLocationListener {
                mlog.v(it.city)
                to_choose_city.text = it.city.replace("市", "")
                EasyHttp.get("weatherInfo?")
                        .baseUrl("https://restapi.amap.com/v3/weather/")
                        .params("city", it.adCode)
                        .params("key", "dc2c5299ff2a28b71482f2831b05f888")
                        .params("extensions", "base")
                        .execute(object : SimpleCallBack<String>() {
                            override fun onSuccess(t: String?) {
                                mlog.v("天气：$t")
                            }

                            override fun onError(e: ApiException?) {

                            }
                        })
            }
        }
    }

    override fun initData() {
        locationClient.startLocation()
        grid.setSwipeItemClickListener { _, position ->
            when (position) {
                0 -> mStartActivity(ZhiYuanActivity::class.java)
                1 -> mStartActivity(TeacherActivity::class.java, Pair("online", true))
                2 -> mStartActivity(VRActivity::class.java)
                3 -> mStartActivity(DataSearchActivity::class.java)
                4 -> mStartActivity(LiuXueActivity::class.java)
                5 -> mStartActivity(ZhaoShengActivity::class.java)
                6 -> mStartActivity(NoteBookActivity::class.java)
                7 -> mStartActivity(TeacherActivity::class.java)
                8 -> mStartActivity(OnlineVideoActivity::class.java)
                9 -> mStartActivity(HeightSchoolActivity::class.java)
            }
        }
        to_choose_city.setOnClickListener { mStartActivity(CityListActivity::class.java) }
        grid.adapter = CommonRecyclerAdapter(layoutInflater, R.layout.item_index_grid, DataUtils.formatIndexGridData())
        listView.adapter = adapter
        pageUrl = Url.Publish.List
        fetchPageData()
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("path","jtjy")
    }
    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Publish.List -> {
                GsonUtil.parseRows(result, ItemNews::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(FindDetailsActivity::class.java)
        }
        show_operation.setOnClickListener {
            IndexOperationPopup(it).show()
        }
        to_zhuanti.setOnClickListener { mStartActivity(ZhuanTiActivity::class.java) }
        to_search.setOnClickListener { mStartActivity(SearchActivity::class.java) }
    }
}