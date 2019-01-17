package pro.haichuang.learn.home.ui.fragment

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.fragment_index.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.ui.activity.CityListActivity
import pro.haichuang.learn.home.ui.activity.find.FindDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.*
import pro.haichuang.learn.home.utils.DataUtils

@ContentView(R.layout.fragment_index)
class IndexFragment : BaseFragment() {

    override fun initData() {
        grid.setSwipeItemClickListener { _, position ->
            when (position) {
                0 -> mStartActivity(ZhiYuanActivity::class.java)
                1 -> mStartActivity(OnlineTeacherActivity::class.java)
                2 -> mStartActivity(VRActivity::class.java)
                3 -> mStartActivity(DataSearchActivity::class.java)
                4 -> mStartActivity(LiuXueActivity::class.java)
                5 -> mStartActivity(ZhaoShengActivity::class.java)
                6 -> mStartActivity(NoteBookActivity::class.java)
                7 -> mStartActivity(XinLiActivity::class.java)
                8 -> mStartActivity(OnlineVideoActivity::class.java)
                9 -> mStartActivity(HeightSchoolActivity::class.java)
            }
        }
        to_choose_city.setOnClickListener { mStartActivity(CityListActivity::class.java) }
        grid.adapter = CommonRecyclerAdapter(layoutInflater, R.layout.item_index_grid, DataUtils.formatIndexGridData())
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_index_list, arrayListOf(1, 2, 3, 4, 5, 6))
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(FindDetailsActivity::class.java)
        }
        to_zhuanti.setOnClickListener { mStartActivity(ZhuanTiActivity::class.java) }
    }
}