package com.zhangying.oem1688.adpter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.OemkefuBean;
import com.zhangying.oem1688.util.AutoForcePermissionUtils;
import com.zhangying.oem1688.util.ToastUtil;

public class MyCustomerServiceAdpter extends BaseRecyclerAdapter<OemkefuBean.RetvalBean.OemkefuBean2.KflistBean> {
    private Context context;

    public MyCustomerServiceAdpter(Context context) {
        this.context = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.mycustomerservice;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, OemkefuBean.RetvalBean.OemkefuBean2.KflistBean item) {

        TextView name_tv = holder.findViewById(R.id.name_tv);
        name_tv.setText(item.getKfname());
        TextView phone_tv = holder.findViewById(R.id.phone_tv);
        phone_tv.setText(item.getKftel());

        holder.findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoForcePermissionUtils.requestPermissions((FragmentActivity) context, new AutoForcePermissionUtils.PermissionCallback() {

                    @Override
                    public void onPermissionGranted() {
                        if (!TextUtils.isEmpty(item.getKftel())) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + item.getKftel());
                            intent.setData(data);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtil.showToast("拨打电话权限被拒绝，请手动拨打！");
                    }
                }, Manifest.permission.CALL_PHONE);
            }
        });
    }
}
