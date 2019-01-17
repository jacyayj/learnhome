/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package pro.haichuang.learn.home.adapter

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.item_square_image.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.ImageBean

class ReleaseImageAdapter(private val context: Activity) : RecyclerView.Adapter<ReleaseImageAdapter.CommonHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private val data by lazy { ArrayList<String>() }

    private fun remove(position:Int) {
        this.data.removeAt(position)
        notifyItemRemoved(position)
        if (position != data.size)
            notifyItemRangeChanged(position, data.size - position)
    }

    fun insert(path: String) {
        data.add(0, path)
        if (data.size == 9)
            notifyItemRemoved(8)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommonHolder(DataBindingUtil.inflate(inflater, R.layout.item_square_image, parent, false))

    override fun getItemCount(): Int = if (data.size < 9) data.size + 1 else 9

    override fun onBindViewHolder(holder: CommonHolder, position: Int) {
        val canDelete = position != data.size
        val item = ImageBean()
        item.canDelete = canDelete
        holder.itemView.apply {
            image.setOnClickListener {
                if (canDelete.not())
                    PictureSelector.create(this@ReleaseImageAdapter.context)
                            .openGallery(PictureConfig.TYPE_IMAGE)
                            .compress(true)
                            .maxSelectNum(9 - data.size)
                            .isCamera(true)
                            .previewImage(true)
                            .selectionMode(PictureConfig.MULTIPLE)
                            .forResult(PictureConfig.CHOOSE_REQUEST)
            }
            delete.setOnClickListener {
                remove(position)
            }
        }
        holder.binding.let {
            if (canDelete)
                item.url = data[position]
            it.setVariable(BR.item, item)
            it.executePendingBindings()
        }
    }

    inner class CommonHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}
