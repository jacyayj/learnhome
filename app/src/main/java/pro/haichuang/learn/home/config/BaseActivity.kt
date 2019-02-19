package pro.haichuang.learn.home.config

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import com.jacy.kit.config.RootActivity
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.layout_title.*
import pro.haichuang.learn.home.net.MyCallBack

/**
 * Created by jacy on 2018/7/24.
 * 根activity，初始化各种通用数据；
 */
abstract class BaseActivity : RootActivity() {

    internal val titleModel by lazy { TitleModel() }


    /**
     * 初始化标题
     */
    open fun initTitle() {
        title_layout?.let {
            titleModel.onLeftClick = {
                onBackPressed()
            }
            val b = DataBindingUtil.bind<ViewDataBinding>(it, DataBindingUtil.getDefaultComponent())
            b?.setVariable(com.android.databinding.library.baseAdapters.BR.title, titleModel)
        }
    }


    fun <T> post(url: String, params: HttpParams, showLoading: Boolean = true) {
        request(url, params, MyCallBack<T>(this, showLoading))
    }
}
