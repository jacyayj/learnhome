package pro.haichuang.learn.home.ui.activity

import android.app.Activity
import android.content.Intent
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_city_list.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.CityListAdapter
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.weight.SideBar


@ContentView(R.layout.activity_city_list)
class CityListActivity : BaseActivity(), AMapLocationListener {
    override fun onLocationChanged(p0: AMapLocation?) {
        p0?.let {
            local.text = "当前城市:${it.city}"
            adapter.setLocal(it.city)
        }
    }

    private val locationOption by lazy {
        AMapLocationClientOption().apply {
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            interval = 2000
        }
    }

    private val locationClient by lazy {
        AMapLocationClient(this).apply {
            setLocationOption(locationOption)
            setLocationListener(this@CityListActivity)
        }
    }


    private val adapter by lazy { CityListAdapter(this) }

    override fun initData() {
        listView.adapter = adapter
        adapter.result = {
            setResult(Activity.RESULT_OK, Intent().putExtra("city", it))
            finish()
        }
//        slide.letters = adapter.letters
        locationClient.startLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.onDestroy()
    }

    override fun initListener() {
//        slide.setOnTouchLetterChangeListener {
//            val position = adapter.getChoosePosition(it)
//            if (position != -1)
//                listView.setSelection(position)
//        }
        slide.setOnTouchingLetterChangedListener(object : SideBar.OnTouchingLetterChangedListener {
            override fun onTouchingLetterChanged(s: String) {
                val position = adapter.getChoosePosition(s)
                if (position != -1)
                    listView.setSelection(position)
            }
        })
    }
}
