package pro.haichuang.learn.home.ui.activity.find

import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_personal_index.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.viewmodel.PersonalIndexModel


@ContentView(R.layout.activity_personal_index)
class PersonalIndexActivity : DataBindingActivity<PersonalIndexModel>(), AMapLocationListener {
    override fun onLocationChanged(p0: AMapLocation?) {
        p0?.let {
            city.text = "城市：${it.province} ${it.city}"
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
            setLocationListener(this@PersonalIndexActivity)
        }
    }
    private lateinit var headerUrl: String


    override fun onResume() {
        post(Url.User.Info, showLoading = false, needSession = true)
        super.onResume()
    }

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_personal_index, arrayListOf(1, 2, 5, 3, 3, 5, 5))
        locationClient.startLocation()
    }

    override fun initListener() {
        listView.setOnItemClickListener { parent, view, position, id ->
            mStartActivity(FindDetailsActivity::class.java)
        }
    }
}



