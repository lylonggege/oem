package com.zhangying.oem1688.view.activity.my;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebStorage;
import android.widget.RelativeLayout;
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
import com.zhangying.oem1688.view.activity.entry.LoginActivity;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.OnClick;

public class SetActivity extends BaseActivity {
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.cache_tv)
    TextView cache_tv;
    @BindView(R.id.version_tv)
    TextView versionTV;
    @BindView(R.id.bangdingshouji_rl)
    RelativeLayout bindMobileRL;
    @BindView(R.id.re_password_rl)
    RelativeLayout updatePwdRL;
    @BindView(R.id.cancel_rl)
    RelativeLayout cancelRL;
    @BindView(R.id.bangdingshouji_top)
    View bindMobileTop;
    @BindView(R.id.bangdingshouji_line)
    View bindMobileLine;
    @BindView(R.id.re_password_line)
    View updatePwdLine;

    private String version;
    private String apkPath;
    private long apkSize;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("设置");
        if (!LoginActivity.hasLogin()){
            bindMobileRL.setVisibility(View.GONE);
            updatePwdRL.setVisibility(View.GONE);
            cancelRL.setVisibility(View.GONE);
            bindMobileTop.setVisibility(View.GONE);
            bindMobileLine.setVisibility(View.GONE);
            updatePwdLine.setVisibility(View.GONE);
        }

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
                            VersionBean.RetvalBean retval = data.getRetval();
                            String remoteV = retval.getVersion();
                            String downUlr = retval.getUrl();
                            if (StringUtils.isEmity(remoteV) || StringUtils.isEmity(version) || StringUtils.isEmity(downUlr) || version.equals(remoteV)){
                                ToastUtil.showToast("当前已是最新版本");
                            }else {
                                //去下载最新版
                                apkSize = retval.getIsize();
                                showUpdaloadDialog(retval.getUrl());
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
        new XPopup.Builder(this)
                .hasShadowBg(true)
                .asConfirm("版本升级", "发现新版本！请及时更新",
                        "取消", "确定",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                startUpload(downloadUrl);//下载最新的版本程序
                            }
                        }, null, false)
                .show();
    }

    //3、下载新版apk
    private void startUpload(String downloadUrl){
        XHttp.downLoad(downloadUrl)
                .savePath(PathUtils.getExtPicturesPath())
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(SetActivity.this, "正在下载新版本...", true);
                        HProgressDialogUtils.setMax(apkSize);
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
                        applyInstallCheck(path);
                    }
                });
    }

    //4、下载完成安装apk，给系统发送一个intent
    private void openAPK(String fileSavePath){
        Intent intent = new Intent();
        File apkFile = new File(fileSavePath);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                String ss = "com.zhangying.oem1688.fileprovider";
                Uri contentUri = FileProvider.getUriForFile(this, ss, apkFile);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            intent.setDataAndType(Uri.parse("file://"+fileSavePath),"application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
           startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.parse("file://"+fileSavePath),"application/vnd.android.package-archive");
//        startActivity(intent);
    }

    //3.1判断是否有安装权限
    private void applyInstallCheck(String path){
        apkPath = path;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0
            //来判断应用是否有权限安装apk
            boolean installAllowed = getPackageManager().canRequestPackageInstalls();
            if (installAllowed) {//有权限
                openAPK(path);//安装apk
            } else {
                ActivityCompat.requestPermissions(this, new String[]   {Manifest.permission.REQUEST_INSTALL_PACKAGES},
                        998);
            }
        } else {//8.0以下
            openAPK(path);//安装apk
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 998 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            openAPK(apkPath);
        }else {
            new XPopup.Builder(this)
                    .hasShadowBg(true)
                    .asConfirm("安装权限", "需要打开允许来自此来源，请去设置中开启此权限",
                            "取消", "确定",
                            new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    toInstallPermissionSettingIntent();
                                }
                            }, null, false)
                    .show();
        }
    }

    //打开安装权限的设置界面
    private void toInstallPermissionSettingIntent() {
        Uri packageURI = Uri.parse("package:"+getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
        startActivityForResult(intent, 996);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 996) {
            openAPK(apkPath);
        }
    }

}