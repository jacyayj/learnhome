package pro.haichuang.learn.home.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.view.View
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.weather.LocalWeatherForecastResult
import com.amap.api.services.weather.LocalWeatherLiveResult
import com.amap.api.services.weather.WeatherSearch
import com.amap.api.services.weather.WeatherSearchQuery
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.mStartActivityForResult
import com.jacy.kit.utils.toDownInt
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.fragment_index.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.AdBean
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.config.Constants.NEWS_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.CityListActivity
import pro.haichuang.learn.home.ui.activity.find.FindDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.*
import pro.haichuang.learn.home.ui.activity.news.NewsDetailsActivity
import pro.haichuang.learn.home.ui.dialog.IndexOperationPopup
import pro.haichuang.learn.home.ui.fragment.itemview.ItemNews
import pro.haichuang.learn.home.utils.DataUtils
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.ImageBinding


@ContentView(R.layout.fragment_index)
class IndexFragment : BaseFragment(), WeatherSearch.OnWeatherSearchListener, AMapLocationListener, View.OnClickListener {

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
            setLocationListener(this@IndexFragment)
        }
    }

    private val weatherSearch by lazy {
        WeatherSearch(context).apply {
            setOnWeatherSearchListener(this@IndexFragment)
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
        grid.adapter = CommonRecyclerAdapter(layoutInflater, R.layout.item_index_grid, DataUtils.formatIndexGridData())
        listView.adapter = adapter
        pageUrl = Url.Publish.List
        fetchPageData()
        post(Url.Ad.List, showLoading = false)
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("path", "jtjy")
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Ad.List -> {
                GsonUtil.parseArray(result, AdBean::class.java).apply {
                    find { it.adspaceId == 1 }?.let {
                        banner.setTag(R.id.tag_1, it.category)
                        banner.setTag(R.id.tag_2, it.link)
                        ImageBinding.displayMatchNet(banner, it.image)
                    }
                    find { it.adspaceId == 2 }?.let {
                        to_zhuanti.setTag(R.id.tag_1, it.category)
                        to_zhuanti.setTag(R.id.tag_2, it.link)
                        ImageBinding.displayMatchNet(to_zhuanti, it.image)
                    }
                    find { it.adspaceId == 3 }?.let {
                        to_kaoyan.setTag(R.id.tag_1, it.category)
                        to_kaoyan.setTag(R.id.tag_2, it.link)
                        ImageBinding.displayMatchNet(to_kaoyan, it.image)
                    }
                    find { it.adspaceId == 4 }?.let {
                        to_dingzhi.setTag(R.id.tag_1, it.category)
                        to_dingzhi.setTag(R.id.tag_2, it.link)
                        ImageBinding.displayMatchNet(to_dingzhi, it.image)
                    }
                }
            }
            Url.Publish.List -> {
                GsonUtil.parseRows(result, ItemNews::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }

    override fun onLocationChanged(p0: AMapLocation?) {
        p0?.let {
            to_choose_city.text = it.city.replace("市", "")
            weatherSearch.query = WeatherSearchQuery(it.city, WeatherSearchQuery.WEATHER_TYPE_LIVE)
            weatherSearch.searchWeatherAsyn()
            locationClient.stopLocation()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onWeatherLiveSearched(p0: LocalWeatherLiveResult?, p1: Int) {
        p0?.liveResult?.let {
            weather.text = it.weather + "\n" + it.temperature + "°"
        }
    }

    override fun onWeatherForecastSearched(p0: LocalWeatherForecastResult?, p1: Int) {
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(FindDetailsActivity::class.java)
        }
        show_operation.setOnClickListener {
            IndexOperationPopup(it).show()
        }
        to_choose_city.setOnClickListener { mStartActivityForResult(CityListActivity::class.java, 0x01) }
        banner.setOnClickListener(this)
        to_zhuanti.setOnClickListener(this)
        to_kaoyan.setOnClickListener(this)
        to_dingzhi.setOnClickListener(this)
        to_search.setOnClickListener { mStartActivity(SearchActivity::class.java) }
    }

    override fun onClick(v: View?) {
        when (v?.getTag(R.id.tag_1)?.toString()) {
            "channel" -> {
                when (v.getTag(R.id.tag_2)?.toString()) {
                    "judge" -> mStartActivity(ZhiYuanActivity::class.java)
                    "online-teacher" -> mStartActivity(TeacherActivity::class.java, Pair("online", true))
                    "vr-college" -> mStartActivity(VRActivity::class.java)
                    "college" -> mStartActivity(DataSearchActivity::class.java)
                    "foreign-college" -> mStartActivity(LiuXueActivity::class.java)
                    "zzzs" -> mStartActivity(ZhaoShengActivity::class.java)
                    "note" -> mStartActivity(NoteBookActivity::class.java)
                    "note" -> mStartActivity(TeacherActivity::class.java)
                    "video" -> mStartActivity(OnlineVideoActivity::class.java)
                    "gxdz" -> mStartActivity(HeightSchoolActivity::class.java)
                    "lecture" -> mStartActivity(ZhuanTiActivity::class.java)
                    "news" -> mStartActivity(ZhuanTiActivity::class.java)
                }
            }
            "content" -> {
                mStartActivity(NewsDetailsActivity::class.java, Pair("isContent", true), Pair(NEWS_ID, v.getTag(R.id.tag_1).toDownInt()))
            }
            "url" -> {
                val intent = Intent("android.intent.action.VIEW")
                intent.data =  Uri.parse(v.getTag(R.id.tag_2).toString())
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 0x01) {
            data?.let {
                val city = it.getStringExtra("city")
                to_choose_city.text = city.replace("市", "")
                weatherSearch.query = WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE)
                weatherSearch.searchWeatherAsyn()
                locationClient.stopLocation()
            }
        }
    }

}