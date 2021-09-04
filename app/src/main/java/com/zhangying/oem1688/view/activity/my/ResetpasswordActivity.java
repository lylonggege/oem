package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetpasswordActivity extends BaseActivity {


    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.et_phone_number)
    MaterialEditText etPhoneNumber;
    @BindView(R.id.et_verify_code)
    MaterialEditText etVerifyCode;
    @BindView(R.id.btn_get_verify_code)
    RoundButton btnGetVerifyCode;
    @BindView(R.id.fl_verify_code)
    FrameLayout flVerifyCode;
    @BindView(R.id.new_pwd_number)
    MaterialEditText newPwdNumber;
    @BindView(R.id.factory_line)
    FrameLayout factory_line;
    private CountDownButtonHelper mCountDownHelper;
    private int type;  //0为找回密码  1绑定手机号

    @Override
    protected int getLayoutId() {
        return R.layout.activity_resetpassword;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("TYPE", 0);
        mCountDownHelper = new CountDownButtonHelper(btnGetVerifyCode, 60);
        if (type==1){
            factory_line.setVisibility(View.GONE);
            titleTV.setText("绑定手机号");
        }else {
            titleTV.setText("找回密码");
        }
    }

    @OnClick({R.id.bacK_RL, R.id.logoff_tv, R.id.btn_get_verify_code})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.btn_get_verify_code:
                if (etPhoneNumber.validate()) {
                    getVerifyCode(etPhoneNumber.getEditValue());
                }
                break;
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.logoff_tv:
                if (etPhoneNumber.validate()) {
                    if (etVerifyCode.validate()) {
                        bind_mobile(etPhoneNumber.getEditValue(), etVerifyCode.getEditValue(),newPwdNumber.getEditValue());
                    }
                }
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode(String phoneNumber) {
        mCountDownHelper.start();
        send_code(phoneNumber);
    }

    private void send_code(String mobile) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ly", "app");
        map.put("mobile", mobile);
        map.put("type", type);
        RemoteRepository.getInstance()
                .send_code(map)
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                    @Override
                    protected void success(BaseBean data) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    private void bind_mobile(String mobile, String code,String pwd) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ly", "app");
        map.put("phone", mobile);
        map.put("code", code);
        if (type==0){
            map.put("pwd", pwd);
        }
        //绑定手机号
        if (type==1){
            RemoteRepository.getInstance()
                    .bind_mobile(map)
                    .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                        @Override
                        protected void success(BaseBean data) {
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

                        }
                    });
        }else {
            RemoteRepository.getInstance()
                    .find_pwd(map)
                    .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                        @Override
                        protected void success(BaseBean data) {
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

                        }
                    });
        }

    }

    //1 代表绑定手机号  0代表找回密码
    public static void simpleActivity(Context context, int type) {
        Intent intent = new Intent(context, ResetpasswordActivity.class);
        intent.putExtra("TYPE", type);
        context.startActivity(intent);
    }


}