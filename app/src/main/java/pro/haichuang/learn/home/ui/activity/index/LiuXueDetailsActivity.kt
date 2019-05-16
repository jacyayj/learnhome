package pro.haichuang.learn.home.ui.activity.index

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.GsonBuilder
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.yhy.widget.layout.flow.tag.TagFlowAdapter
import com.yhy.widget.layout.flow.tag.TagFlowLayout
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_liu_xue_details.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.ImageBean
import pro.haichuang.learn.home.config.Constants.COLLEGE_ID
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.viewmodel.LiuXueDetailsModel
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.mlog


@ContentView(R.layout.activity_liu_xue_details)
class LiuXueDetailsActivity : DataBindingActivity<LiuXueDetailsModel>() {

    private val id by lazy { intent.getIntExtra(COLLEGE_ID, -1).toString() }

    override fun initData() {
        val params = HttpParams()
        params.put("id", id)
        post(Url.ForeignCollege.Get, params)
    }

    override fun initListener() {
        to_zixun.setOnClickListener {
            mStartActivity(LiuXueZiXunActivity::class.java, Pair(COLLEGE_ID, id))
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        mlog.v(GsonBuilder().setPrettyPrinting().create().toJson(result))
        notifyModel(GsonUtil.parseObject(result, LiuXueDetailsModel::class.java))
        banner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView) {
                Glide.with(this@LiuXueDetailsActivity)
                        .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
                        .load(Url.image_base_url + (path as ImageBean).picPaths)
                        .into(imageView)
            }
        })
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.setIndicatorGravity(BannerConfig.CENTER)
        banner.setImages(model.picArr)
        banner.start()
        intro_view.loadData(model.intro)
        intro_view.settings.textZoom = 250
        (tag as TagFlowLayout<String>).setAdapter(object : TagFlowAdapter<String>(model.hotMajor.split(",")) {
            override fun getView(parent: TagFlowLayout<*>?, position: Int, data: String?): View {
                val view = layoutInflater.inflate(R.layout.item_tag, parent, false)
                (view as TextView).text = data
                return view
            }
        })
    }
}
