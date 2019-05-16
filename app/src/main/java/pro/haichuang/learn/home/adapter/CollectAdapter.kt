package pro.haichuang.learn.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemZhuanTiModel
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.CollectModel
import pro.haichuang.learn.home.ui.fragment.itemview.ItemNews
import pro.haichuang.learn.home.utils.GsonUtil

class CollectAdapter(private var context: Activity, private var data: ArrayList<CollectModel> = ArrayList()) : BaseAdapter() {

    private val chooseData by lazy { ArrayList<CollectModel>() }
    private val originData by lazy { ArrayList<CollectModel>() }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val item = data[position]
        val layoutId = when (item.contentType) {
            1 -> R.layout.item_personal_index
            2 -> R.layout.item_height_school_search
            4 -> R.layout.item_zhuanti
            else -> R.layout.item_collect_chat
        }
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false)

        when (item.contentType) {
            1 -> binding.setVariable(BR.item, GsonUtil.parseObject(item.content, ItemNews::class.java))
            2 -> binding.setVariable(BR.item, GsonUtil.parseObject(item.content, CollegeModel::class.java))
            4 -> binding.setVariable(BR.item, GsonUtil.parseObject(item.content, ItemZhuanTiModel::class.java))
            else -> binding.setVariable(BR.item, 0)
        }
        binding.executePendingBindings()
        return binding.root
    }

    fun insertData(d: MutableList<CollectModel>) {
        data.addAll(d)
        originData.addAll(d)
        notifyDataSetChanged()
    }

    fun refresh(data: MutableList<CollectModel>) {
        this.data = ArrayList(data)
        originData.addAll(data)
        notifyDataSetChanged()
    }

    fun doSearch(keyWord: String) {
        data = ArrayList(if (keyWord.isEmpty())
            originData
        else {
            chooseData.clear()
            data.forEach {
                if (it.content.toString().contains(keyWord))
                    chooseData.add(it)
            }
            chooseData
        })
        notifyDataSetChanged()
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount() = data.size
}