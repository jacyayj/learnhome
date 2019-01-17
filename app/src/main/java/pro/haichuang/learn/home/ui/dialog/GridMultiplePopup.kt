package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.popup_grid_multiple.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DataUtils

class GridMultiplePopup(private val view: View) : PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }
    private val adapter by lazy { CommonAdapter<String>(layoutInflater, R.layout.item_grid_multiple) }

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_grid_multiple, null)
        contentView.grid.adapter = adapter
        contentView.grid.setOnItemClickListener { _, _, position, _ ->
            contentView.all.isChecked = false
        }
        contentView.all.setOnClickListener {
            if (contentView.all.isChecked.not()) {
                contentView.all.isChecked = true
                contentView.grid.setItemChecked(contentView.grid.checkedItemPosition, false)
            }
        }
    }

    fun show(type: Int) {
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
                adapter.refresh(DataUtils.formatProvinceData())
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

}