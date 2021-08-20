package com.zhangying.oem1688.internet;

import android.app.Activity;

import com.zhangying.oem1688.R;
import com.zhangying.oem1688.util.LoadingView;
import com.zhangying.oem1688.util.LogUtil;
import com.zhangying.oem1688.util.ToastUtil;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by xialihao on 2018/11/21.
 * 默认的DisposableSubscriber，封装了错误Toast以及等待loading的显示控制
 */
public abstract class DefaultDisposableSubscriber<T> extends DisposableSubscriber<T> {

    private boolean needLoading = false;
    private Activity mContext;
    private boolean autoDismiss = true;
    private LoadingView loadingView;

    public DefaultDisposableSubscriber() {

    }

    public DefaultDisposableSubscriber(Activity context, boolean needLoading) {
        this.needLoading = needLoading;
        mContext = context;
    }

    abstract protected void success(T data);

    protected void failure(String errMsg) {
        if (errMsg!=null){
            ToastUtil.showToast(errMsg);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (needLoading && mContext != null) {
//            mDialog = new ProgressDialog(mContext);
//            mDialog.setView(View.inflate(mContext , R.layout.view_progress , null));
//            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
//            mDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
//            mDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//            mDialog.show();
//            mDialog.setContentView(View.inflate(mContext , R.layout.view_progress , null));
            loadingView = new LoadingView(mContext, R.style.custom_dialog2);
            loadingView.show();
        }
    }

    @Override
    public void onNext(T t) {
        done(true);
        success(t);
    }

    @Override
    public void onError(Throwable t) {

    }

    private void doFailure(String errMsg) {
        LogUtil.e("Failure -> " , errMsg+"");
        failure(errMsg);
    }

    @Override
    public void onComplete() {

    }

    protected void done(boolean isSuccess) {
        if (autoDismiss) {
            dismiss();
        }

        mContext = null;
//        mDialog = null;
        loadingView = null;
    }

    private void dismiss() {
        if (loadingView != null) {
            try {
//                mDialog.dismiss();
                loadingView.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private getDataAgainListener listener;
    public interface getDataAgainListener{
         void getDataAgain();
    }

    public void setGetDataAgainListener(getDataAgainListener listener){
        this.listener = listener;
    }
}
