package pro.haichuang.learn.home.config

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.RootActivity
import com.jacy.kit.config.toast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.vondear.rxtool.RxEncryptTool
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.layout_title.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.net.MyCallBack
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.utils.SPUtils

/**
 * Created by jacy on 2018/7/24.
 * 根activity，初始化各种通用数据；
 */
abstract class BaseActivity : RootActivity(), OnRefreshLoadMoreListener {

    private val pageParams by lazy { HttpParams() }

    private val refreshLayout by lazy { findViewById<SmartRefreshLayout>(R.id.refresh_layout) }

    private var page = 1
    open var isLoadMore = false
    open var pageUrl = ""

    internal val titleModel by lazy { TitleModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTitle()
        refreshLayout?.setOnRefreshLoadMoreListener(this)
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

    open fun setPageParams(pageParams: HttpParams) {}

    /**
     *@param url 接口地址
     *@param params jiek
     *@param showLoading 接口地址
     *@param needSession 接口地址
     *@param success 接口地址
     */
    fun post(url: String, params: HttpParams = HttpParams(), showLoading: Boolean = true, needSession: Boolean = false, success: () -> Unit = {}) {
        if (needSession)
            SPUtils.session?.let {
                params.put("sessionKey", it)
            }
        EasyHttp.post(url)
                .params(sign(params))
                .execute(MyCallBack(url, this, showLoading, success))
    }

    protected fun sign(params: HttpParams): HttpParams {
        val map = params.urlParamsMap
        var sign = ""
        params.put("appId", "5698402822576322")
        params.put("nonce_str", System.currentTimeMillis().toString())
        map.keys.toSortedSet().forEach {
            if ("sign" != it && !map[it].isNullOrEmpty())
                sign += "$it=${map[it]}&"
        }
        sign += "key=${Url.app_key}"
        params.put("sign", RxEncryptTool.encryptMD5ToString(sign))
        return params
    }

    fun <T> dealRows(adapter: Any, data: MutableList<T>) {
        if (data.isEmpty()) {
            if (isLoadMore) {
                toast("已经到底啦")
                page--
                return
            } else {
                toast("暂无数据")
            }
        }
        when (adapter) {
            is CommonAdapter<*> -> if (isLoadMore)
                (adapter as CommonAdapter<T>).add(data)
            else
                (adapter as CommonAdapter<T>).refresh(data)
            is CommonRecyclerAdapter<*> ->
                if (isLoadMore)
                    (adapter as CommonRecyclerAdapter<T>).insertData(data)
                else
                    (adapter as CommonRecyclerAdapter<T>).refresh(data)
        }
    }

    open fun fetchPageData(loadMore: Boolean = false, showLoading: Boolean = true) {
        isLoadMore = loadMore
        if (loadMore)
            page++
        else
            page = 1
        pageParams.put("pageNo", page.toString())
        setPageParams(pageParams)
        post(pageUrl, pageParams, showLoading, needSession = true)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        fetchPageData(showLoading = false)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        fetchPageData(loadMore = true, showLoading = false)
    }

    override fun onFinish() {
        super.onFinish()
        refreshLayout?.finishLoadMore()
        refreshLayout?.finishRefresh()
    }

    override fun onError(msg: String) {
        toast(msg)
    }
}
