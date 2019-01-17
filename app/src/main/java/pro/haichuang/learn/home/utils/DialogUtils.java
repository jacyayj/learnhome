package pro.haichuang.learn.home.utils;

import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * project:SRC
 * author  TP
 * date:  2016/11/21 11:39
 * describe:
 */

public class DialogUtils {

    public static void initDialogWidth(Window window,float widthScale){
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * widthScale); // 宽度设置为屏幕的占比
        window.setAttributes(p);
    }
    public static void initDialogWidthAndHeight(Window window,float widthScale,float heightScale){
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * widthScale); // 宽度设置为屏幕的占比
        p.height = (int) (d.getHeight() * heightScale); // 高度设置为屏幕的占比
        window.setAttributes(p);
    }

    public static void setBottom(Window window){
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (d.getWidth() * 1); // 宽度设置为屏幕的占比
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(p);
    }
}
