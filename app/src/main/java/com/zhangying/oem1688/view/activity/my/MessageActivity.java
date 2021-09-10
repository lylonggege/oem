package com.zhangying.oem1688.view.activity.my;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xutil.tip.ToastUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.BaseBeancClass;
import com.zhangying.oem1688.bean.MemberInfoBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.EventBusStyeSingleton;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.Base64Util;
import com.zhangying.oem1688.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import butterknife.BindView;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity {
    @BindView(R.id.nike_et)
    EditText nikeEt;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.company_et)
    EditText companyEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.head_imageView)
    RadiusImageView headiv;
    private String path;
    private List<LocalMedia> mSelectList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("个人信息");
        initdata();
    }

    private void initdata() {
        showLoading();
        HashMapSingleton.getInstance().reload();
        RemoteRepository.getInstance()
                .getmemberinfo(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<MemberInfoBean>() {

                    @Override
                    protected void success(MemberInfoBean data) {
                        dissmissLoading();
                        MemberInfoBean.RetvalBean retval = data.getRetval();
                        nikeEt.setText(retval.getNickname());
                        nikeEt.setSelection(retval.getNickname().length());
                        nameEt.setText(retval.getReal_name());
                        companyEt.setText(retval.getShop_name());
                        phoneEt.setText(retval.getPhone_mob());
                        GlideUtil.loadImage(MessageActivity.this, retval.getPortrait(), headiv);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }


    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.bacK_RL, R.id.head_imageView, R.id.submit_tv})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.head_imageView:
                //获取写的权限
                RxPermissions rxPermissions=new RxPermissions(this);
                rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission){
                            if (permission.granted){
                                //申请的权限全部允许
                                doOpenPictureSelector();
                            }else{
                                //只要有一个权限被拒绝，就会执行
                                Toast.makeText(MessageActivity.this, "拒绝", Toast.LENGTH_SHORT).show();
                            }
                        }
                });

                break;
            case R.id.submit_tv:
                showLoading();
                HashMapSingleton.getInstance().reload();
                HashMapSingleton.getInstance().put("realname", nameEt.getText().toString() == null ? "" : nameEt.getText().toString());
                HashMapSingleton.getInstance().put("nickname", nikeEt.getText().toString() == null ? "" : nikeEt.getText().toString());
                HashMapSingleton.getInstance().put("phone", phoneEt.getText().toString() == null ? "" : phoneEt.getText().toString());
                HashMapSingleton.getInstance().put("compname", companyEt.getText().toString() == null ? "" : companyEt.getText().toString());
                HashMapSingleton.getInstance().put("portrait", path == null ? "" : path);
                RemoteRepository.getInstance()
                        .edithyoem(HashMapSingleton.getInstance())
                        .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                            @Override
                            protected void success(BaseBean data) {
                                dissmissLoading();
                                if (data.isDone()) {
                                    ToastUtils.toast(data.getMsg());
                                    //跟新我的界面数据
                                    EventBusStyeSingleton.getInstance().updateMyfragment();
                                    finish();
                                } else {
                                    ToastUtils.toast(data.getMsg());
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                super.onError(t);
                                dissmissLoading();
                            }
                        });
                break;
        }
    }

    //打开照片选择
    private void doOpenPictureSelector(){
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .theme(false ? R.style.XUIPictureStyle_Custom : R.style.XUIPictureStyle)
                .maxSelectNum(1)
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .isCamera(true)
                .enableCrop(false)
                .compress(true)
                .previewEggs(true)
                .selectionMedia(mSelectList)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode:" + resultCode);
        System.out.println("requestCode:" + requestCode);
        System.out.println("data:" + PictureSelector.obtainMultipleResult(data).size());
        //System.out.println("path:" + mSelectList.get(0).getCompressPath());
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    if (mSelectList.size() > 0) {
                        GlideUtil.loadImage(this, mSelectList.get(0).getCompressPath(), headiv);
                    } else {
                        return;
                    }
                    showLoading();
                    String base64File = Base64Util.encodeBase64File(mSelectList.get(0).getCompressPath());
                    HashMapSingleton.getInstance().clear();
                    HashMapSingleton.getInstance().put("ly", "app");
                    HashMapSingleton.getInstance().put("portrait", base64File);

                    RemoteRepository.getInstance()
                            .gravatar(HashMapSingleton.getInstance())
                            .subscribeWith(new DefaultDisposableSubscriber<BaseBeancClass>() {
                                @Override
                                protected void success(BaseBeancClass data) {
                                    dissmissLoading();
                                    path = data.getRetval().getPath();
                                }

                                @Override
                                public void onError(Throwable t) {
                                    super.onError(t);
                                    dissmissLoading();
                                }
                            });
                    break;
                default:
                    break;
            }
        }
    }
}