package pro.haichuang.learn.home.bean

import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import com.previewlibrary.enitity.IThumbViewInfo

class MyThumb(private val url: String) : IThumbViewInfo {
    override fun getUrl() = url

    override fun getVideoUrl(): String? = null

    override fun getBounds() = Rect()

    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MyThumb> = object : Parcelable.Creator<MyThumb> {
            override fun createFromParcel(source: Parcel): MyThumb = MyThumb(source)
            override fun newArray(size: Int): Array<MyThumb?> = arrayOfNulls(size)
        }
    }
}