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
import com.vondear.rxtool.RxTimeTool
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.AreaBean
import pro.haichuang.learn.home.bean.LetterBean
import pro.haichuang.learn.home.utils.mlog
import java.io.File

class CityListAdapter(private var context: Activity) : BaseAdapter() {
    private val A_Z by lazy { arrayOf("定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z") }
    private val citys by lazy {
        JSON.parseArray(RxFileTool.readFile2String(File(RxFileTool.getDiskCacheDir(context), "/city_list.json"), "UTF-8"), AreaBean::class.java)
    }
    private val data by lazy {
        mlog.v("start : ${RxTimeTool.getCurTimeMills()}")
        val temp = ArrayList<LetterBean>()
        A_Z.forEachIndexed { i, letter ->
            val letterBean = LetterBean()
            letterBean.letter = letter
            when (i) {
                0 -> letterBean.areas.add(citys.random().apply { showIcon = true })
                1 -> for (i in 0..6) {
                    letterBean.areas.add(citys.random())
                }
                else -> letterBean.areas.addAll(citys.filter { it.pinyin.startsWith(letter, true) })
            }
            letterBean.areas.sortBy { it.pinyin }
            temp.add(letterBean)
        }
        mlog.v("end : ${RxTimeTool.getCurTimeMills()}")
        temp
    }
    var result: (city: String) -> Unit = {}

    fun getChoosePosition(letter: String) = A_Z.indexOf(letter)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val binding: ViewDataBinding? = if (convertView == null)
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_letter_list, parent, false)
        else
            DataBindingUtil.getBinding(convertView)
        val item = data[position]
        item.areas.forEach {
            it.result = result
        }
        if (position == 0) {
            item.grid = true
        } else if (position == 1) {
            item.grid = true
        }
        binding?.setVariable(BR.item, item)
        binding?.executePendingBindings()
        return binding?.root
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount() = A_Z.size

}