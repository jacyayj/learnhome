package pro.haichuang.learn.home.ui.fragment

import android.app.Activity
import android.content.Intent
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.fragment_mine.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.ui.activity.mine.*
import pro.haichuang.learn.home.ui.dialog.InvateDialog
import pro.haichuang.learn.home.utils.DataUtils
import pro.haichuang.learn.home.utils.ImageBinding

@ContentView(R.layout.fragment_mine)
class MineFragment : BaseFragment() {

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_mine, DataUtils.formatMineListData())
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> mStartActivity(WalletActivity::class.java)
                1 -> mStartActivity(JoinGroupActivity::class.java)
                2 -> mStartActivity(CollectActivity::class.java)
                3 -> mStartActivity(OrderActivity::class.java)
                4 -> mStartActivity(FileActivity::class.java)
                5 -> InvateDialog(context!!).show()
            }
        }
        header.setOnClickListener {
            PictureSelector.create(this).openGallery(PictureConfig.TYPE_IMAGE)
                    .previewImage(true)
                    .selectionMode(PictureConfig.SINGLE)
                    .compress(true)
                    .isCamera(true)
                    .forResult(PictureConfig.CHOOSE_REQUEST)
        }
        to_setting.setOnClickListener {
            mStartActivity(MineSettingActivity::class.java)
        }
        to_my_follow.setOnClickListener {
            mStartActivity(MyFollowActivity::class.java)
        }
        to_my_fans.setOnClickListener {
            mStartActivity(MyFansActivity::class.java)
        }
        to_my_dynamic.setOnClickListener {
            mStartActivity(MyDynamicActivity::class.java)
        }
        to_my_comment.setOnClickListener {
            mStartActivity(MyCommentActivity::class.java)
        }
        to_vip.setOnClickListener {
            mStartActivity(BindVipActivity::class.java)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val url = PictureSelector.obtainMultipleResult(data)[0].compressPath
                    ImageBinding.displayLocal(header, url)
                }
            }
        }
    }
}