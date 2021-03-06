package com.zhangying.oem1688.view.activity.entry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.alpha.XUIAlphaTextView;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.display.Colors;
import com.xuexiang.xutil.tip.ToastUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.EvenBusMessageBean;
import com.zhangying.oem1688.bean.ListHistoryBean;
import com.zhangying.oem1688.bean.PhoneloginBean;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.ILoginCall;
import com.zhangying.oem1688.singleton.EventBusStyeSingleton;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.KeyboardUtil;
import com.zhangying.oem1688.util.LogUtil;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.TokenUtils;
import com.zhangying.oem1688.view.activity.MainActivity;
import com.zhangying.oem1688.view.activity.my.MyWebActivity;
import com.zhangying.oem1688.view.activity.my.ResetpasswordActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.zhangying.oem1688.util.TokenUtils.setToken;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone_number)
    MaterialEditText etPhoneNumber;
    @BindView(R.id.et_verify_code)
    MaterialEditText etVerifyCode;
    @BindView(R.id.btn_get_verify_code)
    RoundButton btnGetVerifyCode;
    @BindView(R.id.fl_verify_code)
    FrameLayout flVerifyCode;
    @BindView(R.id.tv_other_login)
    XUIAlphaTextView tvOtherLogin;
    @BindView(R.id.tv_forget_password)
    XUIAlphaTextView tvForgetPassword;
    @BindView(R.id.btn_login)
    SuperButton btnLogin;
    @BindView(R.id.tv_user_protocol)
    XUIAlphaTextView tvUserProtocol;
    @BindView(R.id.tv_privacy_protocol)
    XUIAlphaTextView tvPrivacyProtocol;
    @BindView(R.id.weixin_iv)
    ImageView weixin_iv;
    @BindView(R.id.qq_iv)
    ImageView qq_iv;
    @BindView(R.id.btn_close)
    ImageView btnClose;
    @BindView(R.id.checksign_imageview)
    ImageView imageCheckSign;
    private CountDownButtonHelper mCountDownHelper;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();
    private int type;
    private PhoneloginBean phoneloginBean;
    private boolean isSelect;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (phoneloginBean != null) {
                    ToastUtils.toast(phoneloginBean.getMsg());
                }
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.initStatusBarStyle(this, false, Colors.TRANSPARENT);
        mCountDownHelper = new CountDownButtonHelper(btnGetVerifyCode, 60);
        type = getIntent().getIntExtra("TYPE", -1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.btn_get_verify_code, R.id.btn_login, R.id.tv_forget_password,R.id.checksign_rl,
              R.id.qq_iv, R.id.weixin_iv, R.id.btn_close,R.id.tv_user_protocol,R.id.tv_privacy_protocol})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.checksign_rl:
                isSelect = !isSelect;
                if (!isSelect) {
                    imageCheckSign.setVisibility(View.GONE);
                } else {
                    imageCheckSign.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_user_protocol://????????????
                MyWebActivity.simpleActivity(this, BuildConfig.URL_AGREEMENT, "????????????");
                break;
            case R.id.tv_privacy_protocol://????????????
                MyWebActivity.simpleActivity(this, BuildConfig.URL_PRIVACY, "????????????");
                break;
            case R.id.btn_close://????????????
                finish();
                break;
            case R.id.btn_get_verify_code://?????????????????????
                if (etPhoneNumber.validate()) {
                    getVerifyCode(etPhoneNumber.getEditValue());
                }
                break;
            case R.id.btn_login://??????????????????
                if (etPhoneNumber.validate()) {
                    if (etVerifyCode.validate()) {
                        if (!isSelect){
                            AppUtils.hideSoftKeyboard(this);
                            ToastUtil.showToast("??????????????????????????????");
                            break;
                        }

                        loginByVerifyCode(etPhoneNumber.getEditValue(), etVerifyCode.getEditValue());
                    }
                }
                break;
            case R.id.tv_forget_password://????????????
                ResetpasswordActivity.simpleActivity(this, 0);
                break;
            case R.id.weixin_iv:
                break;
            case R.id.qq_iv:
                break;
        }
    }

    /**
     * ???????????????
     */
    private void getVerifyCode(String phoneNumber) {
        mCountDownHelper.start();
        send_code(phoneNumber);
    }

    /**
     * ?????????????????????
     *
     * @param phoneNumber ?????????
     * @param verifyCode  ?????????
     */
    private void loginByVerifyCode(String phoneNumber, String verifyCode) {
        phonelogin(phoneNumber, verifyCode);
    }

    private void send_code(String mobile) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ly", "app");
        map.put("mobile", mobile);
        map.put("type", 1);
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

    //???????????????????????????????????????????????????  type ??????????????????????????????
    public static Boolean simpleActivity(Context context, int type) {
        String token = TokenUtils.getToken();
        if (!StringUtils.isEmpty(token)) {
            return true;
        }
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("TYPE", type);
        context.startActivity(intent);
        return false;
    }

    //??????????????????
    public static boolean hasLogin(){
        String token = TokenUtils.getToken();
        if (!StringUtils.isEmpty(token)) {
            return true;
        }

        return false;
    }

    private void phonelogin(String phone, String code) {
        String path = BuildConfig.URL + "?app=member&act=phonelogin&ly=app&" + "phone=" + phone + "&code=" + code;
        // 2 request ?????????get??????
        Request request = new Request.Builder().url(path).build();
        // 3 ??????????????????

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                //????????????
                KeyboardUtil.closeKeybord(etVerifyCode, LoginActivity.this);
                try {
                    String string = response.body().string();
                    LogUtil.e("url===", string);
                    Gson gson = new Gson();
                    phoneloginBean = gson.fromJson(string, PhoneloginBean.class);
                    if (phoneloginBean.isDone()) {
                        PhoneloginBean.RetvalBean retval = phoneloginBean.getRetval();
                        String token = retval.getToken();
                        if (token != null) {
                            setToken(token);
                            if (type==3){
                                //??????????????????
                                EventBusStyeSingleton.getInstance().updateMyfragment();
                            }else {
                                EvenBusMessageBean evenBusMessageBean = new EvenBusMessageBean();
                                evenBusMessageBean.setType(type);
                                EventBus.getDefault().post(evenBusMessageBean);
                            }
                            finish();
                        } else {
                            ToastUtil.showToast(phoneloginBean.getMsg());
                        }
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                } catch (Exception exception) {

                }
            }
        });

    }

    public void post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string();
                    LogUtil.e("url===", string);
                    Gson gson = new Gson();
                    PhoneloginBean phoneloginBean = gson.fromJson(string, PhoneloginBean.class);
                    if (phoneloginBean.isDone()) {
                        PhoneloginBean.RetvalBean retval = phoneloginBean.getRetval();
                        String token = retval.getToken();
                        if (token != null) {
                            setToken(token);
                            ActivityUtils.startActivity(MainActivity.class);
                        } else {
                            ToastUtil.showToast("???????????????????????????...");
                        }
                    }

                } catch (Exception exception) {
                    //  ToastUtil.showToast("????????????????????????");
                    finish();
                }
            }
        });

    }

}