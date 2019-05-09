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

class GridMultiplePopup(private val view: View, private val needId: Boolean = true, result: (code: String,name:String) -> Unit) : PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {

    private var type = -1

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }
    private val adapter by lazy { CommonAdapter<NameId>(layoutInflater, R.layout.item_grid_multiple) }

    private var lastCheckedPosition = -1
    private var checkedPosition = -1

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_grid_multiple, null)
        contentView.grid.adapter = adapter

        contentView.grid.setOnItemClickListener { _, _, position, _ ->
            contentView.all.isChecked = false
            checkedPosition = position
        }
        contentView.all.setOnClickListener {
            if (contentView.all.isChecked.not()) {
                contentView.all.isChecked = true
                contentView.grid.setItemChecked(checkedPosition, false)
                checkedPosition = -1
            }
        }
        contentView.confirm.setOnClickListener {
            if (lastCheckedPosition != checkedPosition) {
                lastCheckedPosition = checkedPosition
                if (checkedPosition == -1)
                    result("","全部")
                else
                    adapter.getItem(checkedPosition).let {
                        result(if (needId) it.id.toString() else it.name.replace("省", ""),it.name)
                    }
            }
            dismiss()
        }
    }

    /**
     * @param type
     *  0 : 学校级别选择
     *  1 : 学校类型选择
     *  2 : 省份选择
     *  3 : 批次选择
     */
    fun show(type: Int) {
        showAsDropDown(view)
        if (this.type == -1)
            when (type) {
                0 -> {
                    contentView.grid.numColumns = 4
                    adapter.refresh(DataUtils.levelData)
                }
                1 -> {
                    contentView.grid.numColumns = 5
                    adapter.refresh(DataUtils.typeData)
                }
                2 -> {
                    contentView.grid.numColumns = 5
                    adapter.refresh(DataUtils.provinceData)
                }
                3 -> {
                    contentView.grid.numColumns = 3
                    adapter.refresh(DataUtils.piCiData)
                }
            }
        else {
            if (lastCheckedPosition != checkedPosition) {
                if (lastCheckedPosition == -1) {
                    contentView.all.isChecked = true
                    contentView.grid.clearChoices()
                } else {
                    contentView.all.isChecked = false
                    contentView.grid.setItemChecked(lastCheckedPosition, true)
                }
            }
        }
        this.type = type
    }

    fun show(data: ArrayList<NameId>) {
        showAsDropDown(view)
        if (type == -1)
            adapter.refresh(data)
        else {
            if (lastCheckedPosition != checkedPosition) {
                if (lastCheckedPosition == -1) {
                    contentView.all.isChecked = true
                    contentView.grid.clearChoices()
                } else {
                    contentView.all.isChecked = false
                    contentView.grid.setItemChecked(lastCheckedPosition, true)
                }
            }
        }
        this.type = 5
    }
}