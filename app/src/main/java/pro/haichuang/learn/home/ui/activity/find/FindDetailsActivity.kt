package pro.haichuang.learn.home.ui.activity.find

import com.jacy.kit.adapter.CommonRecyclerAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.jacy.kit.weight.KeyboardLayout
import com.vondear.rxtool.RxKeyboardTool
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_find_details.*
import kotlinx.android.synthetic.main.item_find_details_comment.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.itemmodel.CommentModel
import pro.haichuang.learn.home.ui.activity.find.viewmodel.FindDetailsModel
import pro.haichuang.learn.home.ui.dialog.ShareDialog
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_find_details)
class FindDetailsActivity : DataBindingActivity<FindDetailsModel>() {
    private val commentAdapter by lazy {
        CommonRecyclerAdapter<CommentModel>(layoutInflater, R.layout.item_find_details_comment, ArrayList()) { v, t, _ ->
            v.to_index.setOnClickListener {
                return@setOnClickListener
                mStartActivity(PersonalIndexActivity::class.java)
            }
            v.up.setOnClickListener {
                val params = HttpParams()
                params.put("commentId", t.id.toString())
                params.put("operate", if (t.hasUp) "0" else "1")
                post(Url.Comment.Up, params, needSession = true) {
                    if (t.hasUp)
                        t.ups--
                    else
                        t.ups++
                    t.hasUp = t.hasUp.not()
                }
            }
            v.comment.setOnClickListener {
                model.parentId = t.id.toString()
                comment_et.hint = "回复@" + t.commenter?.realname
                RxKeyboardTool.showSoftInput(this, comment_et)
            }
            t.child?.let {
                v.comment_child.adapter = CommonRecyclerAdapter(layoutInflater,
                        R.layout.item_find_details_comment_child, it) { _, t1, _ ->
                    t1.parentName = t.commenter?.realname ?: ""
                }
            }
        }
    }
    private val params by lazy {
        HttpParams().apply {
            put("id", model.id.toString())
        }
    }


    override fun initData() {
        model.id = intent.getIntExtra(Constants.NEWS_ID, -1)
        listView.adapter = commentAdapter
        post(Url.Publish.Get, params, needSession = true)
        pageUrl = Url.Comment.List
        fetchPageData()
    }

    override fun initListener() {
        to_index.setOnClickListener {
            return@setOnClickListener
            mStartActivity(PersonalIndexActivity::class.java)
        }
        share.setOnClickListener {
            ShareDialog(this, model.title, model.shareUrl, model.txt).show()
        }
        collect.setOnClickListener {
            params.put("operate", if (model.hasCollect) "0" else "1")
            post(Url.Content.Collect, params, needSession = true)
        }
        follow.setOnClickListener {
            model.author?.let {
                val params = HttpParams()
                params.put("attentionUserId", it.userId.toString())
                params.put("operate", if (it.hasAttention) "0" else "1")
                post(Url.Friend.Attention, params, needSession = true) {
                    it.hasAttention = it.hasAttention.not()
                    toast(if (it.hasAttention) "关注成功" else "取消关注成功")
                }
            }

        }
        up.setOnClickListener {
            val params = HttpParams()
            params.put("id", model.id.toString())
            params.put("operate", if (model.hasUp) "0" else "1")
            post(Url.Content.Up, params, needSession = true)
        }
        send.setOnClickListener {
            autoPost(Url.Comment.Save, needSession = true)
        }
        keyboard.setOnkbdStateListener(object : KeyboardLayout.onKeyboardChangeListener {
            override fun onKeyBoardStateChange(state: Int) {
                if (state == KeyboardLayout.KEYBOARD_STATE_HIDE) {
                    comment_et.hint = "写点什么吧…"
                    comment_et.setText("")
                    model.parentId = ""
                }
            }
        })
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Publish.Get -> {
                notifyModel(GsonUtil.parseObject(result, FindDetailsModel::class.java))
            }
            Url.Content.Collect -> {
                model.hasCollect = model.hasCollect.not()
                toast(if (model.hasCollect) "收藏成功" else "取消收藏成功")
            }
            Url.Comment.Save -> {
                toast("评论成功")
                refresh_layout.autoRefresh()
            }
            Url.Comment.List -> {
                GsonUtil.parseRows(result, CommentModel::class.java).list?.let {
                    if (it.isEmpty()) {
                        if (!isLoadMore)
                            status_layout.showEmpty()
                        else
                            dealRows(commentAdapter, it)
                    } else {
                        dealRows(commentAdapter, it)
                        status_layout.showSuccess()
                    }
                }
            }
            Url.Content.Up -> {
                model.hasUp = model.hasUp.not()
            }
        }
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("contentId", model.id.toString())
    }
}
