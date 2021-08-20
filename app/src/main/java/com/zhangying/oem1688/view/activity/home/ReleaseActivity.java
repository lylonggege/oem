package com.zhangying.oem1688.view.activity.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.ImageSelectGridAdapter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.CcatesJsonBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.internet.Utils;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.Base64Util;
import com.zhangying.oem1688.util.SpacesItemDecoration;
import com.zhangying.oem1688.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布页面
 */
public class ReleaseActivity extends BaseActivity implements ImageSelectGridAdapter.OnAddPicClickListener {

    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.content_et)
    EditText content_et;
    @BindView(R.id.MyRecycleView)
    com.zhangying.oem1688.custom.MyRecycleView recyclerView;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.compa_et)
    EditText compaEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.cate_et)
    TextView catetv;
    @BindView(R.id.release_tv)
    TextView releaseTv;
    private String[] option;
    private String[][] mTimeOption1;
    private String[] option_id;
    private String[][] mTimeOption1_id;
    private boolean isboolean;
    private String moptions1;
    private String moptions2;
    private ImageSelectGridAdapter mAdapter;
    private List<LocalMedia> mSelectList = new ArrayList<>();
    private List<String> base64ListFile = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("发布");
        GridLayoutManager manager = new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false);
        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
        recyclerView.addItemDecoration(new SpacesItemDecoration(space, space));
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter = new ImageSelectGridAdapter(ReleaseActivity.this, this));
        mAdapter.setSelectList(mSelectList);
        mAdapter.setSelectMax(8);
        mAdapter.setOnItemClickListener((position, v) -> PictureSelector.create(ReleaseActivity.this).themeStyle(R.style.XUIPictureStyle).openExternalPreview(position, mSelectList));
        initdata();
    }

    private void initdata() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("ly","app");
        RemoteRepository.getInstance()
                .cates_json(map)
                .subscribeWith(new DefaultDisposableSubscriber<CcatesJsonBean>() {

                    @Override
                    protected void success(CcatesJsonBean data) {
                        dissmissLoading();
                        option = new String[data.getRetval().size()];
                        mTimeOption1 = new String[data.getRetval().size()][];
                        option_id = new String[data.getRetval().size()];
                        mTimeOption1_id = new String[data.getRetval().size()][];
                        List<CcatesJsonBean.RetvalBean> retval = data.getRetval();
                        for (int i = 0; i < retval.size(); i++) {
                            CcatesJsonBean.RetvalBean retvalBean = retval.get(i);
                            option[i] = retvalBean.getName();
                            option_id[i] = retvalBean.getId();
                            String[] time = new String[retval.get(i).getChildren().size()];
                            String[] time_id = new String[retval.get(i).getChildren().size()];
                            List<CcatesJsonBean.RetvalBean.ChildrenBean> children = retvalBean.getChildren();
                            for (int i1 = 0; i1 < children.size(); i1++) {
                                time[i1] = children.get(i1).getName();
                                time_id[i1] = children.get(i1).getId();
                            }
                            mTimeOption1[i] = time;
                            mTimeOption1_id[i] = time_id;

                        }


                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    @OnClick({R.id.bacK_RL, R.id.release_tv, R.id.cate_LL})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.cate_LL:
                isboolean = true;
                OptionsPickerView pvOptions = new OptionsPickerBuilder(ReleaseActivity.this, (v, options1, options2, options3) -> {
                    moptions1 = option_id[options1];
                    String[] strings = mTimeOption1_id[options1];
                    moptions2 = strings[options2];
                    catetv.setText("  " + option[options1] + "    " + mTimeOption1[options1][options2]);
                    return false;
                })
                        .setTitleText("")
                        .isRestoreItem(true)
                        .setSelectOptions(0, 0)
                        .build();
                pvOptions.setPicker(option, mTimeOption1);
                pvOptions.show();
                break;
            case R.id.release_tv:

                String content = content_et.getText().toString();
                if (content == null || content.length() == 0) {
                    ToastUtil.showToast("请填写需求内容");
                    return;
                }

                String name = nameEt.getText().toString();
                if (name == null || name.length() == 0) {
                    ToastUtil.showToast("请输入您的姓名");
                    return;
                }

                String companyname = compaEt.getText().toString();
                if (companyname == null || companyname.length() == 0) {
                    ToastUtil.showToast("请输入公司名称");
                    return;
                }

                String phone = phoneEt.getText().toString();
                if (phone == null || phone.length() == 0) {
                    ToastUtil.showToast("请输入您的电话");
                    return;
                }

                if (!isboolean || moptions1 == null || moptions2 == null) {
                    ToastUtil.showToast("请选择品类");
                    return;
                }
                showLoading();
                HashMap<String, Object> map = new HashMap<>();
                map.put("content", content);
                map.put("type", 2);
                map.put("compname", companyname);
                map.put("name", name);
                map.put("phone", phone);
                map.put("cfrom", 7);
                map.put("maxcate", moptions1);  //代工大品类编号.
                map.put("mincate", moptions2);  //代工小品类编号.
                map.put("cate_name", catetv.toString());
                base64ListFile.clear();
                if (mSelectList.size() > 0) {
                    for (LocalMedia localMedia : mSelectList) {
                        String compressPath = localMedia.getCompressPath();
                        String base64File = Base64Util.encodeBase64File(compressPath);
                        base64ListFile.add(base64File);
                    }
                }
                map.put("images", base64ListFile);
                RemoteRepository.getInstance()
                        .demandadd(map)
                        .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
                            @Override
                            protected void success(BaseBean data) {
                                dissmissLoading();
                                if (data.getMsg().equals("发布成功,等待审核!")) {
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


    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, ReleaseActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    mAdapter.setSelectList(mSelectList);
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAddPicClick() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .theme(false ? R.style.XUIPictureStyle_Custom : R.style.XUIPictureStyle)
                .maxSelectNum(8)
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
}