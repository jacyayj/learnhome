package pro.haichuang.learn.home.adapter

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.alibaba.fastjson.JSON
import com.vondear.rxtool.RxFileTool
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.AreaBean
import pro.haichuang.learn.home.bean.LetterBean
import java.io.File

class CityListAdapter(private var context: Activity) : BaseAdapter() {
    private val letters by lazy { arrayListOf("定位", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z") }
    private val citys by lazy {
        JSON.parseArray(RxFileTool.readFile2String(File(RxFileTool.getDiskCacheDir(context), "/city_list.json"), "UTF-8"), AreaBean::class.java)
    }
    val data by lazy {
        val temp = ArrayList<LetterBean>()
        val emptyTemp = ArrayList<String>()
        letters.forEachIndexed { i, letter ->
            val letterBean = LetterBean()
            letterBean.letter = letter
            when (i) {
                0 -> letterBean.areas.add(AreaBean().apply {
                    city_name = "定位中..."
                    showIcon = true
                })
                else -> letterBean.areas.addAll(citys.filter { it.pinyin.startsWith(letter, true) })
            }
            letterBean.areas.sortBy { it.pinyin }
            if (letterBean.areas.isNotEmpty())
                temp.add(letterBean)
            else
                emptyTemp.add(letter)
        }
        temp
    }

    fun setLocal(city: String) {
        data[0].areas[0].city_name = city
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val binding: ViewDataBinding? = if (convertView == null)
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_letter_list, parent, false)
        else
            DataBindingUtil.getBinding(convertView)
        val item = data[position]
        if (position == 0)
            item.grid = true
        binding?.setVariable(BR.item, item)
        binding?.executePendingBindings()
        return binding?.root
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount() = letters.size

}