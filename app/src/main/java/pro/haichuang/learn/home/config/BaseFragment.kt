package pro.haichuang.learn.home.config

import android.os.Bundle
import android.view.View
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.RootFragment
import com.jacy.kit.config.toast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.vondear.rxtool.RxEncryptTool
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.SearchAdapter
import pro.haichuang.learn.home.net.MyCallBack
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.utils.SPUtils

abstract class BaseFragment : RootFragment(), OnRefreshLoadMoreListener {

    private val pageParams by lazy { HttpParams() }

    private val refreshLayout by lazy { view?.findViewById<SmartRefreshLayout>(R.id.refresh_layout) }

    private var page = 1
    open var isLoadMore = false
    open var pageUrl = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout?.setOnRefreshLoadMoreListener(this)
    }

    fun post(url: String, params: HttpParams = HttpParams(), showLoading: Boolean = true, needSession: Boolean = false) {
        if (needSession)
            SPUtils.session?.let {
                params.put("sessionKey", it)
            }
        EasyHttp.post(url)
                .params(sign(params))
                .execute(MyCallBack(url, this, showLoading))
    }

    open fun setPageParams(pageParams: HttpParams) {}
    open fun fetchPageData(loadMore: Boolean = false, showLoading: Boolean = true) {
        isLoadMore = loadMore
        if (loadMore)
            page++
        else
            page = 1
        pageParams.put("pageNo", page.toString())
        setPageParams(pageParams)
        post(pageUrl, pageParams, showLoading)
    }

    private fun sign(params: HttpParams): HttpParams {
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
                    (adapter as CommonAdapter<T>).refresh(data)
            is SearchAdapter<*> ->
                if (isLoadMore)
                    (adapter as SearchAdapter<T>).add(data)
                else
                    (adapter as SearchAdapter<T>).refresh(data)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        fetchPageData(loadMore = false, showLoading = false)
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
