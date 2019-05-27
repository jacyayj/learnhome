package pro.haichuang.learn.home.ui.fragment

import android.app.Activity
import android.content.Intent
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.uinfo.UserService
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.fragment_mine.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.UserInfo
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.*
import pro.haichuang.learn.home.ui.dialog.InvateDialog
import pro.haichuang.learn.home.utils.DataUtils
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.ImageBinding
import pro.haichuang.learn.home.utils.SPUtils
import java.io.File

@ContentView(R.layout.fragment_mine)
class MineFragment : BaseFragment(), AMapLocationListener {
    override fun onLocationChanged(p0: AMapLocation?) {
        p0?.let {
            city.text = "城市：${it.province} ${it.city}"
        }
    }

    private val locationOption by lazy {
        AMapLocationClientOption().apply {
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            interval = 2000
        }
    }

    private val locationClient by lazy {
        AMapLocationClient(context).apply {
            setLocationOption(locationOption)
            setLocationListener(this@MineFragment)
        }
    }
    private lateinit var headerUrl: String
    override fun onResume() {
        post(Url.User.Info, showLoading = false, needSession = true)
        super.onResume()
    }

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_mine, DataUtils.formatMineListData().apply {
            if (SPUtils.isTeacher)
                removeAt(4)
        })
        locationClient.startLocation()
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.User.Info -> {
                val user = GsonUtil.parseObject(result, UserInfo::class.java)
                follow_count.text = user.totalAttention.toString()
                fans_count.text = user.totalFans.toString()
                release_count.text = user.totalPublish.toString()
                comment_count.text = user.totalComment.toString()
                if (SPUtils.isTeacher) {
                    name.text = user.teachername
                    ImageBinding.displayNet(header, user.teacherImg)
                    to_vip.gone()
                } else {
                    name.text = user.realname
                    ImageBinding.displayNet(header, user.userImg)
                    to_vip.setImageResource(if (user.isVip) R.drawable.icon_vip else R.drawable.icon_vip_not)
                }
            }
            Url.Upload.Upload -> {
                val params = HttpParams()
                headerUrl = GsonUtil.getString(result, "uploadPath")
                params.put("userImg", headerUrl)
                post(Url.User.UpdateInfo, params, showLoading = true, needSession = true)
            }
            Url.User.UpdateInfo -> {
                val filed = HashMap<UserInfoFieldEnum, String>()
                filed[UserInfoFieldEnum.AVATAR] = headerUrl
                NIMClient.getService(UserService::class.java).updateUserInfo(filed as Map<UserInfoFieldEnum, Any>?).setCallback(object : RequestCallback<Void> {
                    override fun onSuccess(p0: Void?) {
                        toast("上传成功")
                    }

                    override fun onFailed(p0: Int) {
                        toast("上传失败 $p0")
                    }

                    override fun onException(p0: Throwable?) {
                        toast("上传出错 ${p0?.message}")
                    }
                })
            }
        }
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> mStartActivity(WalletActivity::class.java)
                1 -> mStartActivity(JoinGroupActivity::class.java)
                2 -> mStartActivity(CollectActivity::class.java)
                3 -> if (SPUtils.isTeacher) mStartActivity(OrderTeacherActivity::class.java) else mStartActivity(OrderActivity::class.java)
                4 -> if (SPUtils.isTeacher) InvateDialog(activity!!).show() else mStartActivity(FileActivity::class.java)
                5 -> InvateDialog(activity!!).show()
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
            startActivityForResult(Intent(context, MineSettingActivity::class.java).putExtra("name", name.text.toString()), 0x01)
        }
        to_my_follow.setOnClickListener {
            mStartActivity(MyFollowActivity::class.java)
        }
        to_my_fans.setOnClickListener {
            mStartActivity(MyFansActivity::class.java)
        }
        to_my_article.setOnClickListener {
            mStartActivity(MyArticleActivity::class.java)
        }
        to_my_comment.setOnClickListener {
            mStartActivity(MyCommentActivity::class.java)
        }
        to_vip.setOnClickListener {
            if (SPUtils.isVip)
                toast("您已是VIP")
            else
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
                    val params = HttpParams()
                    params.put("mobile", SPUtils.phone)
                    params.put("type", "image")
                    params.put("uploadFile", File(url), null)
                    post(Url.Upload.Upload, params, showLoading = true)
                }
                else -> post(Url.User.Info, showLoading = false, needSession = true)
            }
        }
    }
}