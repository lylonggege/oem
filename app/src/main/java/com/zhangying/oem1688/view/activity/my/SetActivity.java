package com.zhangying.oem1688.view.activity.my;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebStorage;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.callback.DownloadProgressCallBack;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.display.HProgressDialogUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.VersionBean;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.EventBusStyeSingleton;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.CacheUtil;
import com.zhangying.oem1688.util.StringUtils;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.TokenUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SetActivity extends BaseActivity {
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.cache_tv)
    TextView cache_tv;
    @BindView(R.id.version_tv)
    TextView versionTV;

    private String version;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("设置");
        try {
            String totalCacheSize = CacheUtil.getTotalCacheSize(this);
            cache_tv.setText(totalCacheSize);
        } catch (Exception e) {
            cache_tv.setText("0KB");
            e.printStackTrace();
        }

        //获取新版本
        String ver = getLocalVersionName(this);
        if (!StringUtils.isEmity(ver)){
            version = ver;
            versionTV.setText("V" + ver);
        }
    }

    //获取本地版本名称
    public String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, SetActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.bacK_RL, R.id.bangdingshouji_rl, R.id.checkv_rl,R.id.cancel_rl,
            R.id.re_password_rl, R.id.about_rl, R.id.contact_rl, R.id.clearcache_rl, R.id.logoff_tv})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.cancel_rl://注销账号
                CancelAccountActivity.simpleActivity(this, BuildConfig.URL + "?app=news&act=notice&id=79", "注销须知");
                break;
            case R.id.checkv_rl://检查更新
                checkHasNewVersion();
                break;
            case R.id.bacK_RL://返回
                finish();
                break;
            case R.id.bangdingshouji_rl://绑定手机
                ResetpasswordActivity.simpleActivity(this, 1);
                break;
            case R.id.re_password_rl://修改密码
                ResetpasswordActivity.simpleActivity(this, 0);
                break;
            case R.id.about_rl://关于
                MyAboutDGBActivity.simpleActivity(this);
                break;
            case R.id.contact_rl://联系我们
                MyCustomerServiceActivity.simpleActivity(this);
                break;
            case R.id.clearcache_rl://清除缓存
                CacheUtil.clearAllCache(this);
                CookieSyncManager.createInstance(this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.removeSessionCookie();
                CookieSyncManager.createInstance(this.getApplicationContext());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cookieManager.removeSessionCookies(null);
                    cookieManager.removeAllCookie();
                    cookieManager.flush();
                } else {
                    cookieManager.removeSessionCookies(null);
                    cookieManager.removeAllCookie();
                    CookieSyncManager.getInstance().sync();
                }

                WebStorage.getInstance().deleteAllData();

                try {
                    String totalCacheSize = CacheUtil.getTotalCacheSize(this);
                    cache_tv.setText(totalCacheSize);
                } catch (Exception e) {
                    cache_tv.setText("0KB");
                    e.printStackTrace();
                }
                break;
            case R.id.logoff_tv://退出登录
                new XPopup.Builder(this)
                        .hasShadowBg(true)
                        .asConfirm("提示", "退出后将不能查看留言信息，确定退出登录吗?",
                                "取消", "确定",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        ajax_logout();
                                    }
                                }, null, false)
                        .show();
                break;
        }
    }

    //退出登录
    private void ajax_logout() {
        HashMapSingleton.getInstance().reload();
        RemoteRepository.getInstance()
                .ajax_logout(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                    @Override
                    protected void success(BaseBean data) {
                        if (data.isDone()) {
                            TokenUtils.clearToken();
                            EventBusStyeSingleton.getInstance().updateMyfragment();
                            finish();
                            //AppManagerUtil.getInstance().finishAllActivity();
                            //ActivityUtils.startActivity(MainActivity.class);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    //1、检查是否有新版本
    private void checkHasNewVersion(){
        showLoading();
        RemoteRepository.getInstance()
                .get_version()
                .subscribeWith(new DefaultDisposableSubscriber<VersionBean>() {
                    @Override
                    protected void success(VersionBean data) {
                        dissmissLoading();
                        if (data.isDone()) {
                            String remoteV = data.getRetval().getVersion();
                            String downUlr = data.getRetval().getUrl();
                            if (StringUtils.isEmity(remoteV) || StringUtils.isEmity(version) || StringUtils.isEmity(downUlr) || version.equals(remoteV)){
                                ToastUtil.showToast("当前已是最新版本");
                            }else {
                                //去下载最新版
                                showUpdaloadDialog(data.getRetval().getUrl());
                            }
                        }else {
                            ToastUtil.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        ToastUtil.showToast("版本更新检查失败");
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    //2、弹出提示是否更新apk
    private void showUpdaloadDialog(final String downloadUrl){
        // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置提示框的标题
        builder.setTitle("版本升级").
                setIcon(R.mipmap.ic_launcher). // 设置提示框的图标
                setMessage("发现新版本！请及时更新").// 设置要显示的信息
                setPositiveButton("确定", new DialogInterface.OnClickListener() {// 设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startUpload(downloadUrl);//下载最新的版本程序
            }
        }).setNegativeButton("取消", null);//设置取消按钮,null是什么都不做，并关闭对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();
    }

    //3、下载新版apk
    private void startUpload(String downloadUrl){
        XHttp.downLoad(downloadUrl)
                .savePath(PathUtils.getExtPicturesPath())
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(SetActivity.this, "正在下载新版本...", true);
                    }

                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.toast(e.getMessage());
                        HProgressDialogUtils.cancel();
                    }

                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        HProgressDialogUtils.onLoading(contentLength, bytesRead); //更新进度条
                    }

                    @Override
                    public void onComplete(String path) {
                        ToastUtils.toast("下载完成");
                        HProgressDialogUtils.cancel();
                        openAPK(path);
                    }
                });
    }

    //4、下载完成安装apk，给系统发送一个intent
    private void openAPK(String fileSavePath){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://"+fileSavePath),"application/vnd.android.package-archive");
        startActivity(intent);
    }


}