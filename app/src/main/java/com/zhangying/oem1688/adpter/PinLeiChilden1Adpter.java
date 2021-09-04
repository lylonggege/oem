package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.SitetopinfoBean;
import com.zhangying.oem1688.custom.JumpViewPage;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.onterface.IJumPage;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.view.activity.home.NewProductFactoryActivity;
import com.zhangying.oem1688.view.fragment.ProductFragment;

import java.util.List;

public class PinLeiChilden1Adpter extends BaseRecyclerAdapter<SitetopinfoBean.RetvalBean.childrenBean> {
    private IJumPage jumPage;
    public void setJumPage(IJumPage jumPage) {
        this.jumPage = jumPage;
    }

    private int storeid;
    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }

    private List<SitetopinfoBean.RetvalBean.CatelistBean> catelist;
    public void setCatelist(List<SitetopinfoBean.RetvalBean.CatelistBean> catelist) {
        this.catelist = catelist;
    }

    public PinLeiChilden1Adpter(Context context) {
        this.context = context;
    }

    private Context context;

    private BaseView parentView;
    public void setParentView(BaseView parentView) {
        this.parentView = parentView;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.pinleipopu1;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, SitetopinfoBean.RetvalBean.childrenBean item) {
        LinearLayout rootView = holder.findViewById(R.id.rootView);
        TextView content_tv = holder.findViewById(R.id.name_tv);
        content_tv.setText(item.getCatename());
        ImageView imageView = holder.findViewById(R.id.imageView);
        GlideUtil.loadImage(context, item.getCatelogo(), imageView);
        rootView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                String sid = null;
                int maxId = getMaxCate(item.getCateid());
                System.out.println("cateid:" + maxId + "//itemid:" + item.getCateid());
                if (maxId > 0){
                    sid = maxId + "_" + item.getCateid();
                }else {
                    sid = item.getCateid() + "_" + 0;
                }

                if (context instanceof NewProductFactoryActivity){
                    NewProductFactoryActivity pageActivity = (NewProductFactoryActivity)context;
                    pageActivity.reloadData(sid,item.getCatename());
                }else if (parentView != null && parentView instanceof ProductFragment){
                    ProductFragment pageFragment = (ProductFragment)parentView;
                    pageFragment.reloadData(sid,item.getCatename());
                }else {
                    JumpViewPage jumpViewPage = new JumpViewPage();
                    jumpViewPage.intentActivity(context, 1, sid, item.getCatename(), "工厂");
                }

                jumPage.success();
            }
        });
    }

    private int getMaxCate(int itemId){
        int maxId = 0,itemMaxId = 0;
        for (int i = 0; i < catelist.size(); i++) {
            SitetopinfoBean.RetvalBean.CatelistBean cateBean = catelist.get(i);
            itemMaxId = cateBean.getCateid();
            if (itemMaxId == 0 || itemMaxId == 10000){
                continue;
            }

            if (itemId == itemMaxId) {
                break;
            }

            //设置子类
            List<SitetopinfoBean.RetvalBean.childrenBean> children = cateBean.getChildren();
            for (int j = 0; j < children.size(); j++) {
                SitetopinfoBean.RetvalBean.childrenBean childBean = children.get(j);
                if (itemId== childBean.getCateid()) {
                    maxId = cateBean.getCateid();
                    break;
                }
            }

            if (maxId > 0)break;
        }

        return maxId;
    }
}
