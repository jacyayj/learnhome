package pro.haichuang.learn.home.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import com.jacy.kit.config.*
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.fragment_find.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.SearchAdapter
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.FindDetailsActivity
import pro.haichuang.learn.home.ui.activity.find.FindReleaseActivity
import pro.haichuang.learn.home.ui.activity.login.LoginActivity
import pro.haichuang.learn.home.ui.fragment.itemview.ItemNews
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.ImageBinding
import pro.haichuang.learn.home.utils.SPUtils

@ContentView(R.layout.fragment_find)
class FindFragment : BaseFragment() {
    private lateinit var tabBeans: ArrayList<TabBean>

    private val firstAdapter by lazy { SearchAdapter<ItemNews>(layoutInflater, R.layout.item_find_first) }
    private val otherAdapter by lazy { SearchAdapter<ItemNews>(layoutInflater, R.layout.item_find_other) }

    override fun initData() {
        listView.adapter = firstAdapter
        post(Url.Publish.Channel)
    }

    override fun initListener() {
        clear.setEdit(search_input)
        search_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    clear.gone()
                    if (tab.selectedTabPosition == 0)
                        firstAdapter.doSearch("")
                    else
                        otherAdapter.doSearch("")
                } else {
                    clear.show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        search_input.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (tab.selectedTabPosition == 0)
                    firstAdapter.doSearch(search_input.text.toString())
                else
                    otherAdapter.doSearch(search_input.text.toString())
                true
            } else {
                false
            }
        }

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                fetchPageData()
                if (p0?.position == 0) {
                    ad_view.show()
                    listView.adapter = firstAdapter
                } else {
                    ad_view.gone()
                    listView.adapter = otherAdapter
                }
            }
        })
        to_release.setOnClickListener {
            if (SPUtils.isTourist) {
                mStartActivity(LoginActivity::class.java, Pair("re_login", true))
                toast("请先登录后再操作")
            } else
                mStartActivity(FindReleaseActivity::class.java)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(FindDetailsActivity::class.java, Pair(Constants.NEWS_ID, if (tab.selectedTabPosition == 0) firstAdapter.getItem(position).id else otherAdapter.getItem(position).id))
        }
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("path", tabBeans[tab.selectedTabPosition].path)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Publish.Channel -> {
                pageUrl = Url.Publish.List
                tabBeans = GsonUtil.parseArray(result, TabBean::class.java)
                initTab()
            }
            Url.Publish.List -> {
                val rows = GsonUtil.parseRows(result, ItemNews::class.java)
                rows.list?.let {
                    if (!isLoadMore) {
                        val recommend = it.filter { it.recommend }
                        when (recommend.size) {
                            0 -> ad_view.gone()
                            1 -> {
                                ImageBinding.displayNet(recommend_img_1, recommend[0].picPath)
                                recommend_txt_1.text = recommend[0].title
                                recommend_2.gone()
                            }
                            2 -> {
                                ImageBinding.displayNet(recommend_img_1, recommend[0].picPath)
                                recommend_txt_1.text = recommend[0].title
                                ImageBinding.displayNet(recommend_img_2, recommend[1].picPath)
                                recommend_txt_2.text = recommend[1].title
                            }
                        }
                        it.removeAll(recommend)
                    }
                    dealRows(if (tab.selectedTabPosition == 0) firstAdapter else otherAdapter, it)
                }
            }
        }
    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_vip, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }
}