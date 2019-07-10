package pro.haichuang.learn.home.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jacy.develop.kit.BR
import com.jacy.kit.config.toJson

/**
 * Created by Administrator on 2017/11/7.
 */
class SearchAdapter<T>(
        private val layoutInflater: LayoutInflater,
        private val layoutId: Int,
        var data: MutableList<T> = ArrayList(),
        val function: (view: View, t: T, position: Int) -> Unit = { _, _, _ -> }
) : BaseAdapter() {

    private val chooseData by lazy { ArrayList<T>() }
    private val originData by lazy { ArrayList<T>() }

    fun refresh(data: MutableList<T>) {
        this.data = data
        originData.clear()
        originData.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    fun add(data: MutableList<T>) {
        this.data.addAll(data)
        originData.addAll(data)
        notifyDataSetChanged()
    }


    fun doSearch(keyWord: String) {
        data = ArrayList(if (keyWord.isEmpty())
            originData
        else {
            chooseData.clear()
            data.forEach {
                if (it?.toJson()?.contains(keyWord) == true)
                    chooseData.add(it)
            }
            chooseData
        })
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val binding: ViewDataBinding? = if (convertView == null)
            DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
        else
            DataBindingUtil.getBinding(convertView)
        binding?.setVariable(BR.item, data[position])
        binding?.root?.let { function(it, data[position], position) }
        return binding?.root
    }

    override fun getItem(position: Int): T = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount() = data.size
}
