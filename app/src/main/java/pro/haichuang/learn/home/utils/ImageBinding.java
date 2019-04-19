package pro.haichuang.learn.home.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import pro.haichuang.learn.home.R;
import pro.haichuang.learn.home.net.Url;

public class ImageBinding {


    @BindingAdapter({"color"})
    public static void displayColor(ImageView view, int color) {
        if (color == -1)
            view.setVisibility(View.GONE);
        else
            view.setImageDrawable(new ColorDrawable(color));
    }

    @BindingAdapter({"drawable"})
    public static void displayDrawable(ImageView view, int drawable) {
        if (drawable == -1)
            view.setVisibility(View.GONE);
        else
            view.setImageResource(drawable);
    }

    @BindingAdapter({"local_url"})
    public static void displayLocal(ImageView view, String url) {
        Glide.with(view).applyDefaultRequestOptions(RequestOptions.errorOf(R.mipmap.ic_launcher_round).placeholder(R.mipmap.ic_launcher_round)).load(url).into(view);
    }

    @BindingAdapter({"net_url"})
    public static void displayNet(ImageView view, String url) {
        Glide.with(view)
                .applyDefaultRequestOptions(RequestOptions.errorOf(R.mipmap.ic_launcher_round).placeholder(R.mipmap.ic_launcher_round))
                .load(Url.image_base_url + url)
                .into(view);
    }

    @BindingAdapter({"match_net_url"})
    public static void displayMatchNet(ImageView view, String url) {
        Glide.with(view)
                .applyDefaultRequestOptions(RequestOptions.centerCropTransform().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round))
                .load(Url.image_base_url + url)
                .into(view);
    }

    @BindingAdapter({"release_url"})
    public static void displayReleaseImage(ImageView view, String url) {
        if (!url.isEmpty())
            Glide.with(view).applyDefaultRequestOptions(RequestOptions.centerCropTransform().error(R.mipmap.ic_launcher_round).placeholder(R.mipmap.ic_launcher_round)).load(url).into(view);
    }

}
