package com.zhangying.oem1688.view.activity.my;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.BaseBeancClass;
import com.zhangying.oem1688.bean.CcatesJsonBean;
import com.zhangying.oem1688.bean.ListHistoryBean;
import com.zhangying.oem1688.bean.MemberInfoBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.Base64Util;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.MD5Util;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.TokenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        long timestamp = System.currentTimeMillis() / 1000;
        HashMap<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("token", TokenUtils.getToken());
        String url = timestamp + TokenUtils.getToken() + "&^%$RSTUih09135ZST)(*";
        String md5Str = MD5Util.getMD5Str(url);
        map.put("sign", md5Str);
        RemoteRepository.getInstance()
                .getmemberinfo(map)
                .subscribeWith(new DefaultDisposableSubscriber<MemberInfoBean>() {

                    @Override
                    protected void success(MemberInfoBean data) {
                        dissmissLoading();
                        MemberInfoBean.RetvalBean retval = data.getRetval();
                        nikeEt.setText(retval.getNickname());
                        nameEt.setText(retval.getUser_name());
                        companyEt.setText(retval.getPhone_mob());
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
        switch (view.getId()) {
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.head_imageView:
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
                break;
            case R.id.submit_tv:
                showLoading();
                HashMap<String, Object> map = new HashMap<>();
                map.put("realname", nameEt.getText().toString() == null ? "" : nameEt.getText().toString());
                map.put("nickname", nikeEt.getText().toString() == null ? "" : nikeEt.getText().toString());
                map.put("phone", phoneEt.getText().toString() == null ? "" : phoneEt.getText().toString());
                map.put("compname", companyEt.getText().toString() == null ? "" : companyEt.getText().toString());
                map.put("portrait", path == null ? "" : path);
                long timestamp = System.currentTimeMillis() / 1000;
                map.put("timestamp", timestamp);
                map.put("token", TokenUtils.getToken());
                String url = timestamp + TokenUtils.getToken() + "&^%$RSTUih09135ZST)(*";
                String md5Str = MD5Util.getMD5Str(url);
                map.put("sign", md5Str);
                RemoteRepository.getInstance()
                        .edithyoem(map)
                        .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                            @Override
                            protected void success(BaseBean data) {
                                dissmissLoading();
                                if (data.isDone()) {
                                    ToastUtil.showToast(data.getMsg());
                                    finish();
                                } else {


                                    ToastUtil.showToast(data.getMsg());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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