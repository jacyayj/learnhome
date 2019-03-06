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
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.net.MyCallBack
import pro.haichuang.learn.home.R

abstract class BaseFragment : RootFragment(), OnRefreshLoadMoreListener {

    open val pageParams by lazy { HttpParams() }

    private val refreshLayout by lazy { view?.findViewById<SmartRefreshLayout>(R.id.refresh_layout) }

    private var page = 1
    open var isLoadMore = false
    open var pageUrl = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout?.setOnRefreshLoadMoreListener(this)
    }

    fun post(url: String, params: HttpParams=HttpParams(), showLoading: Boolean = true) {
        EasyHttp.post(url)
                .params(params)
                .execute(MyCallBack(url, this, showLoading))
    }

    fun <T> dealRows(adapter: Any, data: MutableList<T>) {
        if (data.isEmpty()) {
            if (isLoadMore) {
                toast("已经到底啦")
                return
            } else {
                data.clear()
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
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        isLoadMore = false
        page = 1
        pageParams.put("pageNo", page.toString())
        post(pageUrl, pageParams, false)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isLoadMore = true
        page++
        pageParams.put("pageNo", page.toString())
        post(pageUrl, pageParams, false)
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
