package com.zhangying.oem1688.popu;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lxj.xpopup.core.DrawerPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.HomeGoodAdpter;
import com.zhangying.oem1688.adpter.SitetopinfoLeftAdpter;
import com.zhangying.oem1688.bean.CatesBean;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.bean.MoreProstoreBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.onterface.BaseInterfacePosition;
import com.zhangying.oem1688.onterface.ICallBcak;
import com.zhangying.oem1688.util.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GoodsFactoryCatePopu extends DrawerPopupView {

    private MyRecycleView recycleView_left;
    private MyRecycleView recycleView_right;
    private List<FactoryDetailBean.RetvalBean.GcatesBean>  mcatesBean;
    private Context mcontext;
    private HomeGoodAdpter home_goodAdpter;
    private List<HomeBena.RetvalBean.SgoodsListBean.GoodsBean> getGoods = new ArrayList<>();
    private  SitetopinfoLeftAdpter sitetopinfoLeftAdpter;

    public GoodsFactoryCatePopu(@NonNull Context context, List<FactoryDetailBean.RetvalBean.GcatesBean> catesBean) {
        super(context);
        mcatesBean = catesBean;
        mcontext = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.sitetopinfopoopu;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        recycleView_left = findViewById(R.id.recycview_left);
        recycleView_left.setLayoutManager(new LinearLayoutManager(mcontext));
        recycleView_right = findViewById(R.id.recycleView_right);
        FactoryDetailBean.RetvalBean.GcatesBean gcatesBean = mcatesBean.get(0);
        gcatesBean.setIsboolean(true);
        sitetopinfoLeftAdpter=new SitetopinfoLeftAdpter();
        sitetopinfoLeftAdpter.refresh(mcatesBean);
        recycleView_left.setAdapter(sitetopinfoLeftAdpter);
        home_goodAdpter = new HomeGoodAdpter(mcontext);
        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
        recycleView_right.addItemDecoration(new SpacesItemDecoration(space, space));
        recycleView_right.setLayoutManager(new GridLayoutManager(mcontext, 2));
        recycleView_right.setAdapter(home_goodAdpter);

        getGoods.clear();
        FactoryDetailBean.RetvalBean.GcatesBean dataBean = mcatesBean.get(0);
        for (int i = 0; i < dataBean.getGlist().size(); i++) {
            FactoryDetailBean.RetvalBean.GcatesBean.GlistBean glistBean = dataBean.getGlist().get(i);
            HomeBena.RetvalBean.SgoodsListBean.GoodsBean goodsBean = new HomeBena.RetvalBean.SgoodsListBean.GoodsBean();
            goodsBean.setDefault_image(glistBean.getDefault_image());
            goodsBean.setGoods_id(glistBean.getGoods_id());
            goodsBean.setGoods_name("");
            getGoods.add(goodsBean);
        }

        home_goodAdpter.refresh(getGoods);

        sitetopinfoLeftAdpter.setBaseInterfacePosition(new BaseInterfacePosition() {
            @Override
            public void getPosition(int position, Boolean isboolean, View view) {
                Log.e("位置", "getPosition: "+position );
                getGoods.clear();
                for (int i = 0; i < mcatesBean.size(); i++) {
                    FactoryDetailBean.RetvalBean.GcatesBean gcatesBean1 = mcatesBean.get(i);
                    if (position==i){
                        gcatesBean1.setIsboolean(true);
                        for (int i1 = 0; i1 < gcatesBean1.getGlist().size(); i1++) {
                            FactoryDetailBean.RetvalBean.GcatesBean.GlistBean glistBean = gcatesBean1.getGlist().get(i1);
                            HomeBena.RetvalBean.SgoodsListBean.GoodsBean goodsBean = new HomeBena.RetvalBean.SgoodsListBean.GoodsBean();
                            goodsBean.setDefault_image(glistBean.getDefault_image());
                            goodsBean.setGoods_id(glistBean.getGoods_id());
                            goodsBean.setGoods_name("");
                            getGoods.add(goodsBean);
                        }
                    }else {
                        gcatesBean1.setIsboolean(false);
                    }
                    home_goodAdpter.refresh(getGoods);
                }
                sitetopinfoLeftAdpter.notifyDataSetChanged();

            }
        });

        home_goodAdpter.setiCallBcak(new ICallBcak() {
            @Override
            public void success() {
                onDismiss();
            }
        });

    }

    //完全消失执行
    @Override
    protected void onDismiss() {
     super.dismiss();
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext())*.85f);
    }
}
