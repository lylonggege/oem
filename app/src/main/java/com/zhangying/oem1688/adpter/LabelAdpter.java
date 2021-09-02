package com.zhangying.oem1688.adpter;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.MessageListBean;
import com.zhangying.oem1688.bean.MessageViewBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.ICallMobile;
import com.zhangying.oem1688.onterface.IMessageView;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AutoForcePermissionUtils;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.StringUtils;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;

import androidx.annotation.NonNull;

public class LabelAdpter extends BaseRecyclerAdapter<MessageListBean.RetvalBean> {
    private Context context;
    public LabelAdpter(Context context) {
        this.context = context;
    }

    private int type;
    public void setType(int type) {
        this.type = type;
    }

    private ICallMobile iCallMobile;
    public void setiCallMobile(ICallMobile iCallMobile) { this.iCallMobile = iCallMobile; }

    //是否为公司留言
    private boolean isStoreMsg;
    public void setStoreMsg(boolean storeMsg) {
        isStoreMsg = storeMsg;
    }

    private IMessageView iMessageView;
    public void setiMessageView(IMessageView iMessageView) {
        this.iMessageView = iMessageView;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.label;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, MessageListBean.RetvalBean item) {
        TextView name_tv = holder.findViewById(R.id.name_tv);
        TextView address_tv = holder.findViewById(R.id.address_tv);
        TextView time_tv = holder.findViewById(R.id.time_tv);
        TextView see_tv = holder.findViewById(R.id.see_tv);
        name_tv.setText(item.getS_name());
        address_tv.setText(item.getS_areatype());
        if (type == 0) {//最新留言
            time_tv.setText(item.getAdd_time());
            String s_view_ys = item.getS_view_ys();
            if (s_view_ys.equals("1")) {
                see_tv.setTextColor(Color.argb(255, 35, 109, 232));
            } else {
                see_tv.setTextColor(Color.argb(255, 255, 54, 0));
            }
            see_tv.setText(item.getS_view_txt());
        } else if (type == 1){//已读留言
            time_tv.setText(item.getS_mobile());
            see_tv.setText("详情");
            see_tv.setTextColor(Color.parseColor("#428bca"));
        }else {//未读留言
            time_tv.setText(item.getAdd_time());
            see_tv.setTextColor(Color.argb(255, 255, 54, 0));
            see_tv.setText(item.getS_view_nums() + item.getS_view_numtxt());
        }

        RelativeLayout rootView = holder.findViewById(R.id.rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击查看留言信息
                HashMapSingleton.getInstance().reload();
                HashMapSingleton.getInstance().put("id",item.getAgent_id());
                HashMapSingleton.getInstance().put("sid",item.getStore_id());
                HashMapSingleton.getInstance().put("cid",item.getM_store_id());
                String mode = "";
                if (isStoreMsg){//公司留言
                    mode = "gsly";
                } else if (type == 0) {//最新留言
                    mode = "newly";
                } else if (type == 1){//已读留言
                    mode = "viewedly";
                }else {//未读留言
                    mode = "noviewly";
                }
                HashMapSingleton.getInstance().put("vmode",mode);
                HashMapSingleton.getInstance().put("hvd",item.getS_view_ys());

                RemoteRepository.getInstance()
                        .message_view(HashMapSingleton.getInstance())
                        .subscribeWith(new DefaultDisposableSubscriber<MessageViewBean>() {
                            @Override
                            protected void success(MessageViewBean viewBean) {
                                if (viewBean.isDone()){
                                    if (iMessageView != null){
                                        iMessageView.viewPosition(position);;
                                    }

                                    //显示留言弹窗
                                    showMessagePop(viewBean, rootView);

                                    //更新行记录
                                    if ("0".equals(item.getS_view_ys())){
                                        item.setS_view_ys("1");
                                        item.setS_view_txt("已读");

                                        Integer readNums = item.getS_view_nums();
                                        item.setS_view_nums(readNums + 1);
                                        notifyDataSetChanged();
                                    }
                                }else {
                                    ToastUtil.showToast(viewBean.getMsg());
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                super.onError(t);
                                ToastUtil.showToast("服务器参数异常...");
                            }
                        });
            }
        });
    }

    //打开留言弹窗
    private void showMessagePop(MessageViewBean viewBean,RelativeLayout rootView){
        LayoutInflater inflater = LayoutInflater.from(context);// 取得LayoutInflater对象
        View popView = inflater.inflate(R.layout.labepopu, null);
        PopupWindow popupWindow = new PopupWindow(popView, ScreenTools.instance(context).getScreenWidth(), ScreenTools.instance(context).getScreenHeight(), true);
        TextView close_tv = popView.findViewById(R.id.close_tv); // 取得组件
        TextView name_tv = popView.findViewById(R.id.name_tv);
        TextView company_tv = popView.findViewById(R.id.company_tv);
        TextView address_tv = popView.findViewById(R.id.address_tv);
        TextView phone_tv = popView.findViewById(R.id.phone_tv);

        name_tv.setText(viewBean.getRetval().getS_name());
        address_tv.setText(viewBean.getRetval().getS_areatype());
        close_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        //拨打电话
        phone_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iCallMobile != null){
                    iCallMobile.toCall(viewBean.getRetval().getS_mobile());
                }
            }
        });
        company_tv.setText(viewBean.getRetval().getS_yixiang());
        phone_tv.setText(viewBean.getRetval().getS_mobile());
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);        // 显示弹出窗口
    }
}
