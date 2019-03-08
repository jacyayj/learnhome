package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhuanti_details.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.viewmodel.ZhuanTiDetailsModel
import pro.haichuang.learn.home.ui.dialog.ShareDialog
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_zhuanti_details)
class ZhuanTiDetailsActivity : DataBindingActivity<ZhuanTiDetailsModel>() {

    private val id by lazy { intent.getIntExtra(Constants.ZHUANTI_ID, -1) }
    private val params by lazy {
        HttpParams().apply {
            put("id", id.toString())
        }
    }

    override fun initData() {
        post(Url.Lecture.Get, params, needSession = true)
    }

    override fun initListener() {
        to_share.setOnClickListener { ShareDialog(this).show() }
        confirm.setOnClickListener { mStartActivity(ZhuanTiSignupActivity::class.java, Pair(Constants.ZHUANTI_ID, id)) }
        collect.setOnClickListener {
            params.put("operate", if (model.hasCollect) "0" else "1")
            post(Url.Content.Collect, params, needSession = true)
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Content.Collect -> {
                model.hasCollect = model.hasCollect.not()
                toast(if (model.hasCollect) "收藏成功" else "取消收藏成功")
            }
            Url.Lecture.Get -> {
                val model = GsonUtil.parseObject(result, ZhuanTiDetailsModel::class.java)
                notifyModel(model)
            }
        }
    }
}
