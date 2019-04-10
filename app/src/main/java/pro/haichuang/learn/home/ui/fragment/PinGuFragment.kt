package pro.haichuang.learn.home.ui.fragment

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.fragment_pin_gu.*
import kotlinx.android.synthetic.main.item_zuanye_pinggu.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.ui.activity.index.ZhuanYePingGuDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemPingGuModel

@ContentView(R.layout.fragment_pin_gu)
class PinGuFragment : BaseFragment() {


    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_zuanye_pinggu, arrayListOf(ItemPingGuModel(),
                ItemPingGuModel(), ItemPingGuModel(), ItemPingGuModel(), ItemPingGuModel(), ItemPingGuModel())) { v, t, _ ->
            v.grid.adapter = CommonAdapter(layoutInflater, R.layout.item_zuanye_pinggu_child, t.child)
            v.grid.setOnItemClickListener { _, _, _, _ ->
                mStartActivity(ZhuanYePingGuDetailsActivity::class.java)
            }
        }
    }

    override fun initListener() {
    }

    companion object {
        fun newInstance(): PinGuFragment {
            return PinGuFragment()
        }
    }
}
