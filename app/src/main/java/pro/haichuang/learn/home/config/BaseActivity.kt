package pro.haichuang.learn.home.config

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.jacy.kit.config.RootActivity
import com.jacy.kit.config.toast
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.layout_title.*
import pro.haichuang.learn.home.net.MyCallBack

/**
 * Created by jacy on 2018/7/24.
 * 根activity，初始化各种通用数据；
 */
abstract class BaseActivity : RootActivity() {

    internal val titleModel by lazy { TitleModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTitle()
    }

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
        EasyHttp.post(url)
                .params(params)
                .execute(MyCallBack<T>(url,this, showLoading))
    }

    override fun onError(msg: String) {
        toast(msg)
    }
}
