package pro.haichuang.learn.home.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
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

@ContentView(R.layout.fragment_index)
class IndexFragment : BaseFragment(), WeatherSearch.OnWeatherSearchListener, AMapLocationListener {
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
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("path", "jtjy")
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
        to_choose_city.setOnClickListener { mStartActivityForResult(CityListActivity::class.java,0x01) }
        to_zhuanti.setOnClickListener { mStartActivity(ZhuanTiActivity::class.java) }
        to_search.setOnClickListener { mStartActivity(SearchActivity::class.java) }
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