package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.popup_grid_multiple.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.NameId
import pro.haichuang.learn.home.utils.DataUtils

class GridMultiplePopup(val view: View, result: (code: String) -> Unit = {}) : PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {

    private var type = -1

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }
    private val adapter by lazy { CommonAdapter<String>(layoutInflater, R.layout.item_grid_multiple) }

    private val provinceData by lazy { DataUtils.formatProvinceData(view.context) }

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_grid_multiple, null)
        contentView.grid.adapter = adapter
        var checkedPosition = -1
        contentView.grid.setOnItemClickListener { _, _, position, _ ->
            contentView.all.isChecked = false
            checkedPosition = position
        }
        contentView.all.setOnClickListener {
            if (contentView.all.isChecked.not()) {
                contentView.all.isChecked = true
                contentView.grid.setItemChecked(contentView.grid.checkedItemPosition, false)
                checkedPosition = -1
            }
        }
        contentView.confirm.setOnClickListener {
            when (type) {
                5 -> {
                    result(if (checkedPosition == -1) "" else provinceData?.get(checkedPosition)?.id
                            ?: "")
                }
            }
            dismiss()
        }
    }

    /**
     * @param type
     *  0 : 学校排行选择
     *  1 : 学校类型选择
     *  2 : 省份选择
     *  3 : 学校等级选择
     *  4 : 专业选择
     */
    fun show(type: Int) {
        this.type = type
        showAsDropDown(view)
        when (type) {
            0 -> {
                contentView.grid.numColumns = 4
                adapter.refresh(DataUtils.formatSchoolRatingData())
            }
            1 -> {
                contentView.grid.numColumns = 5
                adapter.refresh(DataUtils.formatSchoolTypeData())
            }
            2 -> {
                contentView.grid.numColumns = 5
                provinceData?.toStringData()?.let { adapter.refresh(it) }
            }
            3 -> {
                contentView.grid.numColumns = 3
                adapter.refresh(DataUtils.formatLevelData())
            }
            4 -> {
                contentView.grid.numColumns = 5
                adapter.refresh(DataUtils.formatZuanYeData())
            }
        }
    }

    private fun ArrayList<*>.toStringData(): ArrayList<String> {
        val stringData = ArrayList<String>()
        forEach {
            if (it is NameId) {
                stringData.add(it.name)
            }
        }
        return stringData
    }
}