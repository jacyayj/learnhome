package pro.haichuang.learn.home.ui.activity.index

import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Environment
import android.widget.RelativeLayout
import com.jacy.kit.config.ContentView
import com.tencent.smtt.sdk.TbsReaderView
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.DownloadProgressCallBack
import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_note_book_details.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.NOTE_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.NoteModel
import pro.haichuang.learn.home.utils.GsonUtil
import pro.haichuang.learn.home.utils.mlog


@ContentView(R.layout.activity_note_book_details)
class NoteBookDetailsActivity : BaseActivity(), TbsReaderView.ReaderCallback {


    private val tbsReaderView by lazy { TbsReaderView(this, this) }

    override fun initData() {
        post(Url.Note.Get, HttpParams("id", intent.getIntExtra(NOTE_ID, -1).toString()))
        window.setFormat(PixelFormat.TRANSLUCENT)
        content.addView(tbsReaderView, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT))
    }

    override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {

    }

    override fun onSuccess(url: String, result: Any?) {
        val obj = GsonUtil.parseObject(result, NoteModel::class.java)
        titleModel.title = obj.title
        val name = obj.docPath.split("/")
        EasyHttp.downLoad(Url.image_base_url + obj.docPath).saveName(name[name.lastIndex])
                .execute(object : DownloadProgressCallBack<String>() {
                    override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {

                    }

                    override fun onComplete(path: String?) {
                        val bundle = Bundle()
                        val sur = obj.docPath.split(".")[1]
                        mlog.v("filePath : $path")
                        mlog.v("sur : $sur")
                        bundle.putString("filePath", path)
                        bundle.putString("tempPath", Environment.getExternalStorageDirectory().path)
                        val result = tbsReaderView.preOpen(sur, false)
                        if (result)
                            tbsReaderView.openFile(bundle)
//                QbSdk.openFileReader(this@NoteBookDetailsActivity, path, hashMapOf(Pair("local", "true"))) {
//                    mlog.v("code : $it")
//                }
                        onFinish()
                    }

                    override fun onError(e: ApiException?) {
                        onFinish()
                    }

                    override fun onStart() {
                        onBegin()
                    }
                })
    }

    override fun onDestroy() {
        tbsReaderView.onStop()
        super.onDestroy()
    }
}
