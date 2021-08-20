package com.zhangying.oem1688.view.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.HomeFindFactorChannelAdpter;
import com.zhangying.oem1688.adpter.HomeFindFactorChilderAdpter;
import com.zhangying.oem1688.adpter.HomeFindFactorParentAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.CateAreaListBean;
import com.zhangying.oem1688.bean.CompanyFactoryBean;
import com.zhangying.oem1688.bean.SitetopinfoBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseInterfacePosition;
import com.zhangying.oem1688.popu.PinLeiPopu;
import com.zhangying.oem1688.util.ToastUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 帮我找工厂
 */
public class FindFactoryActivity extends BaseActivity {

    @BindView(R.id.MyRecycleView_parent)
    MyRecycleView MyRecycleViewParent;
    @BindView(R.id.hangye_et)
    EditText hangyeEt;
    @BindView(R.id.MyRecycleView_children)
    MyRecycleView MyRecycleViewChildren;
    @BindView(R.id.aname_et)
    EditText anameEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.send_LL)
    LinearLayout sendLL;
    @BindView(R.id.cata_et)
    EditText cataEt;
    @BindView(R.id.MyRecycleView_channel)
    MyRecycleView MyRecycleViewChannel;
    @BindView(R.id.channel_et)
    EditText channelEt;
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.selected_area_tv)
    TextView selected_area_tv;
    private SitetopinfoBean mSitetopinfoBean;
    //地区
    private String[] option_address;
    private String[][] mTimeOption1_address;
    private String[] option_id_address;
    private String[][] mTimeOption1_id_address;
    private String moptions1_address;
    private String moptions2_address;
    private String regionname;
    private String maxcate;
    private String mincate;
    private int schannel;
    private String maxcate_Text;
    private String mincate_text;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_factory;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("帮我找工厂-代工帮");
        //获取分类数据
        sitetopinfo();
        showLoading();
        HomeFindFactorParentAdpter homeFindFactorParentAdpter = new HomeFindFactorParentAdpter();
        HomeFindFactorChilderAdpter homeFindFactorChilderAdpter = new HomeFindFactorChilderAdpter();
        HomeFindFactorChannelAdpter homeFindFactorChannelAdpter = new HomeFindFactorChannelAdpter();
        WidgetUtils.initGridRecyclerView(MyRecycleViewParent, 4, DensityUtils.dp2px(8));
        WidgetUtils.initGridRecyclerView(MyRecycleViewChildren, 4, DensityUtils.dp2px(8));
        WidgetUtils.initGridRecyclerView(MyRecycleViewChannel, 4, DensityUtils.dp2px(8));
        MyRecycleViewParent.setAdapter(homeFindFactorParentAdpter);
        MyRecycleViewChildren.setAdapter(homeFindFactorChilderAdpter);
        MyRecycleViewChannel.setAdapter(homeFindFactorChannelAdpter);

        RemoteRepository.getInstance()
                .cate_area_list()
                .subscribeWith(new DefaultDisposableSubscriber<CateAreaListBean>() {

                    @Override
                    protected void success(CateAreaListBean data) {
                        dissmissLoading();
                        CateAreaListBean.RetvalBean retval = data.getRetval();
                        for (int i = 0; i < retval.getCates().size(); i++) {
                            if (i == 0) {
                                CateAreaListBean.catesBean catesBean = retval.getCates().get(0);
                                catesBean.setBoolean(true);
                                maxcate = catesBean.getId();
                                maxcate_Text = catesBean.getName();
                                continue;
                            }
                        }
                        homeFindFactorParentAdpter.refresh(retval.getCates());

                        if (retval.getCates().size() > 0) {
                            List<CateAreaListBean.catesBean.childrenBan> children = retval.getCates().get(0).getChildren();
                            homeFindFactorChilderAdpter.refresh(children);
                            homeFindFactorChilderAdpter.setInterfacePosition(new BaseInterfacePosition() {
                                @Override
                                public void getPosition(int position, Boolean isboolean, View view) {
                                    for (int i = 0; i < children.size(); i++) {
                                        CateAreaListBean.catesBean.childrenBan childrenBan = children.get(i);
                                        if (i == position) {
                                            if (childrenBan.isBoolean()) {
                                                childrenBan.setBoolean(false);
                                            } else {
                                                mincate = retval.getCates().get(0).getId();
                                                mincate_text =retval.getCates().get(0).getName();
                                                childrenBan.setBoolean(true);
                                            }
                                        } else {
                                            childrenBan.setBoolean(false);
                                        }
                                    }
                                    homeFindFactorChilderAdpter.notifyDataSetChanged();
                                }
                            });
                        }
                        //根据大类点击获取小类的数据
                        homeFindFactorParentAdpter.setInterfacePosition(new BaseInterfacePosition() {
                            @Override
                            public void getPosition(int position, Boolean isboolean, View view) {
                                if (isboolean) {
                                    CateAreaListBean.catesBean catesBean = retval.getCates().get(position);
                                    MyRecycleViewChildren.setVisibility(View.VISIBLE);
                                    List<CateAreaListBean.catesBean.childrenBan> children = catesBean.getChildren();
                                    homeFindFactorChilderAdpter.refresh(children);
                                    homeFindFactorChilderAdpter.setInterfacePosition(new BaseInterfacePosition() {
                                        @Override
                                        public void getPosition(int position, Boolean isboolean, View view) {
                                            for (int i = 0; i < children.size(); i++) {
                                                CateAreaListBean.catesBean.childrenBan childrenBan = children.get(i);
                                                if (i == position) {
                                                    if (childrenBan.isBoolean()) {
                                                        childrenBan.setBoolean(false);
                                                    } else {
                                                        mincate = catesBean.getId();
                                                        mincate_text = catesBean.getName();
                                                        childrenBan.setBoolean(true);
                                                    }
                                                } else {
                                                    childrenBan.setBoolean(false);
                                                }
                                            }
                                            homeFindFactorChilderAdpter.notifyDataSetChanged();
                                        }
                                    });

                                } else {
                                    MyRecycleViewChildren.setVisibility(View.GONE);
                                }
                                for (int i = 0; i < retval.getCates().size(); i++) {
                                    CateAreaListBean.catesBean catesBean = retval.getCates().get(i);
                                    if (i == position) {
                                        if (catesBean.isBoolean()) {
                                            catesBean.setBoolean(false);
                                        } else {
                                            catesBean.setBoolean(true);
                                            maxcate = catesBean.getId();
                                            maxcate_Text = catesBean.getName();
                                        }
                                    } else {
                                        catesBean.setBoolean(false);
                                    }
                                }
                                homeFindFactorParentAdpter.notifyDataSetChanged();


                            }
                        });
                        List<CateAreaListBean.RetvalBean.SchannelBean.QdlistBean> qdlist = retval.getSchannel().getQdlist();
                        for (int i = 0; i < qdlist.size(); i++) {
                            CateAreaListBean.RetvalBean.SchannelBean.QdlistBean qdlistBean = qdlist.get(i);
                            qdlistBean.setBoolean(false);
                        }
                        //我的渠道
                        homeFindFactorChannelAdpter.refresh(qdlist);

                        homeFindFactorChannelAdpter.setInterfacePosition(new BaseInterfacePosition() {
                            @Override
                            public void getPosition(int position, Boolean isboolean, View view) {
                                List<CateAreaListBean.RetvalBean.SchannelBean.QdlistBean> qdlist = retval.getSchannel().getQdlist();
                                for (int i = 0; i < qdlist.size(); i++) {
                                    CateAreaListBean.RetvalBean.SchannelBean.QdlistBean qdlistBean = qdlist.get(i);
                                    if (i == position) {
                                        if (qdlistBean.getBoolean()) {
                                            qdlistBean.setBoolean(false);
                                        } else {
                                            qdlistBean.setBoolean(true);
                                            schannel = qdlistBean.getId();
                                        }
                                    } else {
                                        qdlistBean.setBoolean(false);
                                    }
                                    homeFindFactorChannelAdpter.notifyDataSetChanged();
                                }
                            }
                        });
                        List<CompanyFactoryBean.RetvalBean.OemareaBean> areas = retval.getAreas();
                        option_address = new String[areas.size()];
                        mTimeOption1_address = new String[areas.size()][];
                        option_id_address = new String[areas.size()];
                        mTimeOption1_id_address = new String[areas.size()][];
                        for (int i = 0; i < areas.size(); i++) {
                            CompanyFactoryBean.RetvalBean.OemareaBean oemareaBean = areas.get(i);
                            option_address[i] = oemareaBean.getRegionname();
                            option_id_address[i] = oemareaBean.getRegionid() + "";
                            String[] time = new String[areas.get(i).getNextList().size()];
                            String[] time_id = new String[areas.get(i).getNextList().size()];
                            List<CompanyFactoryBean.nextListBean> nextList = areas.get(i).getNextList();
                            for (int i1 = 0; i1 < nextList.size(); i1++) {
                                time[i1] = nextList.get(i1).getRegionname();
                                time_id[i1] = nextList.get(i1).getRegionid() + "";
                            }
                            mTimeOption1_address[i] = time;
                            mTimeOption1_id_address[i] = time_id;
                        }

                    }
                });
    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, FindFactoryActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.bacK_RL, R.id.send_LL, R.id.selected_area_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.send_LL:

                String name = anameEt.getText().toString();
                if (name == null || name.length() == 0) {
                    ToastUtil.showToast("请输入姓名");
                    break;
                }

                String phone = phoneEt.getText().toString();
                if (phone == null || phone.length() == 0) {
                    ToastUtil.showToast("请输入电话");
                    break;
                }

                HashMap<String, Object> map = new HashMap<>();
                map.put("maxcate", maxcate);
                map.put("mincate", mincate);
                map.put("catename", maxcate_Text + " " + mincate_text);
                map.put("schannel", schannel);
                map.put("province", moptions1_address);
                map.put("city", moptions2_address);
                map.put("regionname", regionname);
                map.put("type", 1);
                map.put("name", name);
                map.put("phone", phone);
                RemoteRepository.getInstance()
                        .demandadd(map)
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
            case R.id.selected_area_tv:

                if (option_address == null || option_address.length == 0) {
                    return;
                }
                OptionsPickerView pvOptions_address = new OptionsPickerBuilder(this, (v, options1, options2, options3) -> {
                    moptions1_address = option_id_address[options1];
                    String[] strings = mTimeOption1_id_address[options1];
                    moptions2_address = strings[options2];
                    selected_area_tv.setText(option_address[options1]);
                    regionname = option_address[options1] + " " + mTimeOption1_address[options1][options2];
                    return false;
                })
                        .setTitleText("")
                        .isRestoreItem(true)
                        .setSelectOptions(0, 0)
                        .build();
                pvOptions_address.setPicker(option_address, mTimeOption1_address);
                pvOptions_address.show();
                break;
        }
    }

    private void sitetopinfo() {
        RemoteRepository.getInstance()
                .sitetopinfo()
                .subscribeWith(new DefaultDisposableSubscriber<SitetopinfoBean>() {

                    @Override
                    protected void success(SitetopinfoBean sitetopinfoBean) {
                        mSitetopinfoBean = sitetopinfoBean;
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }
}