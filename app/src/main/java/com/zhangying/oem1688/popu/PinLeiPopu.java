package com.zhangying.oem1688.popu;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lxj.xpopup.core.DrawerPopupView;
import com.lxj.xpopup.core.PositionPopupView;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.PinLeiAdpter;
import com.zhangying.oem1688.adpter.PinLeiChilden1Adpter;
import com.zhangying.oem1688.adpter.PinLeiChilden2Adpter;
import com.zhangying.oem1688.bean.SitetopinfoBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.onterface.BaseInterfacePosition;
import com.zhangying.oem1688.onterface.IJumPage;

import java.util.List;

public class PinLeiPopu extends PositionPopupView {
    private SitetopinfoBean msitetopinfoBean;
    private MyRecycleView MyRecycleView_left, MyRecycleView_2, MyRecycleView_1;
    private Context mcontext;
    private PinLeiAdpter pinLeiAdpter;
    private PinLeiChilden1Adpter pinLeiChilden1Adpter;
    private PinLeiChilden2Adpter pinLeiChilden2Adpter;
    private LinearLayout type_ll_1, type_ll_2;
    private TextView content_tv_1, content_tv_2;


    public PinLeiPopu(@NonNull Context context, SitetopinfoBean sitetopinfoBean) {
        super(context);
        this.mcontext = context;
        msitetopinfoBean = sitetopinfoBean;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.areapopup;
    }

    public void refreshData(SitetopinfoBean sitetopinfoBean){
        msitetopinfoBean = sitetopinfoBean;
        SitetopinfoBean.RetvalBean retval = msitetopinfoBean.getRetval();
        if (retval == null) {
            destroy();
            return;
        }
        List<SitetopinfoBean.RetvalBean.CatelistBean> catelist = retval.getCatelist();

        for (int i = 0; i < catelist.size(); i++) {
            if (i == 0) {
                catelist.get(i).setaBoolean(true);
            }

        }

        type_ll_1 = findViewById(R.id.type_ll_1);
        type_ll_2 = findViewById(R.id.type_ll_2);
        content_tv_1 = findViewById(R.id.content_tv_1);
        content_tv_2 = findViewById(R.id.content_tv_2);
        MyRecycleView_1 = findViewById(R.id.MyRecycleView_1);
        MyRecycleView_2 = findViewById(R.id.MyRecycleView_2);
        MyRecycleView_left = findViewById(R.id.MyRecycleView_left);
        WidgetUtils.initGridRecyclerView(MyRecycleView_1, 3, DensityUtils.dp2px(2));
        WidgetUtils.initGridRecyclerView(MyRecycleView_2, 3, DensityUtils.dp2px(2));
        MyRecycleView_left.setLayoutManager(new LinearLayoutManager(mcontext));
        pinLeiAdpter = new PinLeiAdpter();
        type_ll_1.setVisibility(VISIBLE);
        type_ll_2.setVisibility(GONE);

        pinLeiChilden1Adpter = new PinLeiChilden1Adpter(mcontext);
        pinLeiChilden2Adpter = new PinLeiChilden2Adpter(mcontext);
        pinLeiChilden1Adpter.setJumPage(new IJumPage() {
            @Override
            public void success() {
                destroy();
            }
        });
        pinLeiChilden2Adpter.setJumPage(new IJumPage() {
            @Override
            public void success() {
                destroy();
            }
        });
        pinLeiChilden1Adpter.refresh(catelist.get(0).getChildren());
        pinLeiChilden1Adpter.setStoreid(catelist.get(0).getCateid());
        content_tv_1.setText(catelist.get(0).getCatename());
        MyRecycleView_1.setAdapter(pinLeiChilden1Adpter);
        MyRecycleView_2.setAdapter(pinLeiChilden2Adpter);

        pinLeiAdpter.setBaseInterfacePosition(new BaseInterfacePosition() {
            @Override
            public void getPosition(int position, Boolean isboolean, View view) {
                type_ll_1.setVisibility(GONE);
                type_ll_2.setVisibility(GONE);
                SitetopinfoBean.RetvalBean.CatelistBean catelistBean = catelist.get(position);
                if (catelistBean.getCateid() == 0 || catelistBean.getCateid() == 10000) {
                    type_ll_1.setVisibility(View.VISIBLE);
                    content_tv_1.setText(catelistBean.getCatename());
                    for (int i = 0; i < catelist.size(); i++) {
                        if (i == position) {
                            catelist.get(position).setaBoolean(true);

                            int maxId = getMaxCate(catelist,catelistBean.getCateid());
                            //设置子类
                            List<SitetopinfoBean.RetvalBean.childrenBean> children = catelist.get(position).getChildren();
                            pinLeiChilden1Adpter.refresh(children);
                            pinLeiChilden1Adpter.setStoreid(maxId);
                        } else {
                            catelist.get(i).setaBoolean(false);
                        }
                    }
                } else {
                    type_ll_2.setVisibility(View.VISIBLE);
                    content_tv_2.setText(catelistBean.getCatename());
                    for (int i = 0; i < catelist.size(); i++) {
                        if (i == position) {
                            catelist.get(position).setaBoolean(true);
                            //设置子类
                            List<SitetopinfoBean.RetvalBean.childrenBean> children = catelist.get(position).getChildren();
                            pinLeiChilden2Adpter.refresh(children);
                            pinLeiChilden1Adpter.setStoreid(catelist.get(position).getCateid());
                        } else {
                            catelist.get(i).setaBoolean(false);
                        }
                    }
                }

                pinLeiAdpter.notifyDataSetChanged();
            }
        });
        pinLeiAdpter.refresh(catelist);
        MyRecycleView_left.setAdapter(pinLeiAdpter);
    }

    private int getMaxCate(List<SitetopinfoBean.RetvalBean.CatelistBean> catelist, int itemId){
        int maxId = 0;
        for (int i = 0; i < catelist.size(); i++) {
            SitetopinfoBean.RetvalBean.CatelistBean cateBean = catelist.get(i);
            if (itemId == cateBean.getCateid()) {
                break;
            }

            //设置子类
            List<SitetopinfoBean.RetvalBean.childrenBean> children = catelist.get(i).getChildren();
            for (int j = 0; j < children.size(); i++) {
                SitetopinfoBean.RetvalBean.childrenBean childBean = children.get(i);
                if (itemId== childBean.getCateid()) {
                    maxId = cateBean.getCateid();
                    break;
                }
            }

            if (maxId > 0)break;
        }

        return maxId;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        if (msitetopinfoBean == null)return;
    }
}
