package pro.haichuang.learn.home.ui.activity.find

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_find_details.*
import kotlinx.android.synthetic.main.item_find_details_comment.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.bean.ImageBean
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.find.itemmodel.CommentModel
import pro.haichuang.learn.home.ui.activity.find.viewmodel.FindDetailsModel
import pro.haichuang.learn.home.ui.dialog.ShareDialog


@ContentView(R.layout.activity_find_details)
class FindDetailsActivity : DataBindingActivity<FindDetailsModel>() {

    override fun initData() {
        images.adapter = CommonAdapter(layoutInflater, R.layout.item_square_image, arrayListOf(ImageBean(), ImageBean(), ImageBean(), ImageBean(), ImageBean(), ImageBean(), ImageBean()))
        comment.adapter = CommonRecyclerAdapter(layoutInflater, R.layout.item_find_details_comment, arrayListOf(1, 2, 3, 4, 5, 6, 7)) { v, _, _ ->
            v.to_index.setOnClickListener {
                mStartActivity(PersonalIndexActivity::class.java)
            }
            v.comment_child.adapter = CommonRecyclerAdapter(layoutInflater,
                    R.layout.item_find_details_comment_child,
                    arrayListOf(CommentModel(), CommentModel(), CommentModel(), CommentModel()))
        }
    }

    override fun initListener() {
        to_index.setOnClickListener {
            mStartActivity(PersonalIndexActivity::class.java)
        }
        share.setOnClickListener {
            ShareDialog(this).show()
        }
    }
}
