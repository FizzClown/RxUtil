package com.lq.mylibrary;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;



import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lq on 2018/1/24.
 */

public class Request<T extends BaseResponse> {
    private Subscription sp;
    private ProgressDialog progressDialog;

    public void request(Observable<T> observable, final String tag, final Context context, boolean showProgress, final Result<T> result) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, tag + "->没有网络，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }

        if (showProgress) {
            progressDialog = ProgressDialog.show(context, tag, "请稍后...");
        }

        sp = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onCompleted() {
                        if (sp != null && sp.isUnsubscribed()) {
                            sp.unsubscribe();
                        }
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e("FrameDemo", tag + "->onError: " + e.toString());
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        ToastUtil.show(context, tag + "->" + e.toString());
                    }

                    @Override
                    public void onNext(T o) {
                        if (o.status == 1) {
                            result.get(o);
                        } else {
                            ToastUtil.show(context, tag + "->" + o.msg);
                        }
                    }
                });
    }
}
