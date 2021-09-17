package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.onterface.ICallBcak;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.view.activity.home.GoodsDetailActivity;

public class HomeGoodAdpter extends BaseRecyclerAdapter<HomeBena.RetvalBean.SgoodsListBean.GoodsBean> {
    public HomeGoodAdpter(Context context) {
        this.context = context;
    }
    private Context context;

    private ICallBcak iCallBcak;
    public void setiCallBcak(ICallBcak iCallBcak) {
        this.iCallBcak = iCallBcak;
    }

    private int parentWidth;
    public void setParentWidth(int parentWidth) {
        this.parentWidth = parentWidth;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.good_item;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, HomeBena.RetvalBean.SgoodsListBean.GoodsBean item) {
        TextView content_tv = (TextView) holder.findView(R.id.textView_content);
        RadiusImageView imageView = (RadiusImageView) holder.findView(R.id.imageView);
        LinearLayout rootView = holder.findViewById(R.id.rootView);
        rootView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                /**
                 * 其他界面不许需要影藏弹出框的回调
                 */
                if (iCallBcak != null) {
                    iCallBcak.success();
                }
                GoodsDetailActivity.simpleActivity(context, item.getGoods_id());
            }
        });

        if (item.getGoods_name() != null && item.getGoods_name().length() > 0) {
            content_tv.setVisibility(View.VISIBLE);
            content_tv.setText(item.getGoods_name());
        } else {
            content_tv.setVisibility(View.GONE);
        }

        GlideUtil.loadImage(context, item.getDefault_image(), imageView);
        LinearLayout bottom_LL = (LinearLayout) holder.findView(R.id.bottom_LL);

        if (item.getGoods_tags() != null && item.getGoods_tags().size() > 0) {
            bottom_LL.setVisibility(View.VISIBLE);
            bottom_LL.removeAllViews();
            int index = 0;
            for (HomeBena.RetvalBean.SgoodsListBean.GoodsBean.GoodsTagsBean goods_tag : item.getGoods_tags()) {
                TextView textView = new TextView(context);
                bottom_LL.addView(textView);
                try {
                    String sclass = goods_tag.getSclass();
                    if (sclass.equals("bg-orage2")) {
                        textView.setTextColor(Color.parseColor("#ff3600"));
                        int Radii3 = ScreenTools.instance(context).dip2px(3F);
                        float[] radii1 = new float[]{
                                Radii3, Radii3,
                                Radii3, Radii3,
                                Radii3, Radii3,
                                Radii3, Radii3
                        };
                        GradientDrawable gd = new GradientDrawable();//创建drawable
                        gd.setShape(GradientDrawable.RECTANGLE);
                        gd.setColor(Color.parseColor("#ffeee4"));
                        gd.setCornerRadii(radii1);
                        textView.setBackground(gd);
                    } else {
                        textView.setTextColor(Color.parseColor("#ffffff"));
                        int Radii3 = ScreenTools.instance(context).dip2px(3F);
                        float[] radii1 = new float[]{
                                Radii3, Radii3,
                                Radii3, Radii3,
                                Radii3, Radii3,
                                Radii3, Radii3
                        };
                        GradientDrawable gd = new GradientDrawable();//创建drawable
                        gd.setShape(GradientDrawable.RECTANGLE);
                        gd.setColor(Color.parseColor("#ff3600"));
                        gd.setCornerRadii(radii1);
                        textView.setBackground(gd);
                    }
                    textView.setText(goods_tag.getStag());
                    textView.setTextSize(10);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(index == 0 ? 20 : 8, 0, 0, 0);
                    textView.setPadding(5, 3, 5, 3);
                    index ++;
                } catch (Exception e) {

                }
            }
        } else {
            bottom_LL.setVisibility(View.GONE);
        }
    }
}
