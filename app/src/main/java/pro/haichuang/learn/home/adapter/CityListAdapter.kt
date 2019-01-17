package pro.haichuang.learn.home.adapter

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.AreaBean
import pro.haichuang.learn.home.bean.LetterBean
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class CityListAdapter(private var context: Activity) : BaseAdapter() {
    private val A_Z by lazy { arrayOf("定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z") }
    private val areas by lazy { Gson().fromJson<ArrayList<AreaBean>>(readArea(), object : TypeToken<ArrayList<AreaBean>>() {}.type) }
    private val data by lazy {
        val temp = ArrayList<LetterBean>()
        A_Z.forEachIndexed { i, letter ->
            val letterBean = LetterBean()
            letterBean.letter = letter
            when (i) {
                0 -> letterBean.areas.add(areas.random().apply { showIcon = true })
                1 -> for (i in 0..6) {
                    letterBean.areas.add(areas.random())
                }
                else -> letterBean.areas.addAll(areas.filter { it.pinyin.startsWith(letter, true) })
            }

            temp.add(letterBean)
        }
        temp
    }

    fun getChoosePosition(letter: String) = data.indexOf(data.find { it.letter.contains(letter) })

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val binding: ViewDataBinding? = if (convertView == null)
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_letter_list, parent, false)
        else
            DataBindingUtil.getBinding(convertView)
        val item = data[position]
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

    private fun readArea(): String {
        try {
            val inputStreamReader = InputStreamReader(context.resources.assets.open("area.text"), "UTF-8")
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String?
            var result = ""
            while (true) {
                line = bufferedReader.readLine()
                if (line.isNullOrEmpty())
                    break
                result += line
            }
            return result
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }
}