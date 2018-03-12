package com.lq.mylibrary;

import android.content.Context;
import android.widget.Toast;


/**
 * @author lq
 *         Created by lq on 2017/12/15.
 */

public class ToastUtil {
    private static Toast toast;

    public static void show(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
