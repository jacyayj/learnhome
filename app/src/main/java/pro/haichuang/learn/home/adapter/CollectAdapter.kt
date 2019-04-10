package pro.haichuang.learn.home.adapter

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

class CollectAdapter(private var context: Activity) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val layoutId = when (getItemViewType(position)) {
            0 -> R.layout.item_personal_index
            1 -> R.layout.item_height_school_search
            else -> R.layout.item_collect_chat
        }
        val binding: ViewDataBinding? = if (convertView == null)
            DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false)
        else
            DataBindingUtil.getBinding(convertView)
        when (getItemViewType(position)) {
            0 -> binding?.setVariable(BR.item, 0)
            1 -> binding?.setVariable(BR.item, CollegeModel())
            else -> binding?.setVariable(BR.item, 0)
        }
        binding?.executePendingBindings()
        return binding?.root
    }

    override fun getItem(position: Int) = position

    override fun getItemId(position: Int): Long = position.toLong()
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getCount() = 5
}