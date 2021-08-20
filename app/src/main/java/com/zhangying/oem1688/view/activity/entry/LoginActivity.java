package com.zhangying.oem1688.view.activity.entry;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.alpha.XUIAlphaTextView;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.common.RandomUtils;
import com.xuexiang.xutil.display.Colors;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.PhoneloginBean;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.internet.ApiService;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.util.LogUtil;
import com.zhangying.oem1688.util.MD5Util;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.view.activity.MainActivity;
import com.zhangying.oem1688.view.activity.my.ResetpasswordActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

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
    private CountDownButtonHelper mCountDownHelper;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.initStatusBarStyle(this, false, Colors.TRANSPARENT);
        mCountDownHelper = new CountDownButtonHelper(btnGetVerifyCode, 60);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.btn_get_verify_code, R.id.btn_login, R.id.tv_forget_password, R.id.qq_iv, R.id.weixin_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_verify_code:
                if (etPhoneNumber.validate()) {
                    getVerifyCode(etPhoneNumber.getEditValue());
                }

                break;
            case R.id.btn_login:
                if (etPhoneNumber.validate()) {
                    if (etVerifyCode.validate()) {
                        loginByVerifyCode(etPhoneNumber.getEditValue(), etVerifyCode.getEditValue());
                    }
                }
                break;
            case R.id.tv_forget_password:
                ResetpasswordActivity.simpleActivity(this, 0);
                break;
            case R.id.weixin_iv:

                break;
            case R.id.qq_iv:
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

    /**
     * 根据验证码登录
     *
     * @param phoneNumber 手机号
     * @param verifyCode  验证码
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

    private void phonelogin(String phone, String code) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("ly", "app");
//        map.put("phone", phone);
//        map.put("code", code);
//        RemoteRepository.getInstance()
//                .phonelogin(map)
//                .subscribeWith(new DefaultDisposableSubscriber<PhoneloginBean>() {
//
//                    @Override
//                    protected void success(PhoneloginBean data) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        super.onError(t);
//
//                    }
//                });

//        HashMap<Object, Object> map = new HashMap<>();
//        map.put("phone",phone);
//        map.put("code",code);
//
//        JSONObject  jsonObject = new JSONObject(map);
//        try {
//            post(BuildConfig.URL+"?app=member&act=phonelogin&ly=app",jsonObject.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String url = "&ly=app&phone=" + phone + "&code=" + code+"&^%$RSTUih09135ZST)(*";
//        String md5Str = MD5Util.getMD5Str(url);
//        try {
//            post(BuildConfig.URL+"?app=member&act=phonelogin",md5Str);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String path = BuildConfig.URL + "?app=member&act=phonelogin&ly=app&" + "phone=" + phone + "&code=" + code;
        // 2 request 默认是get请求
        Request request = new Request.Builder().url(path).build();
        // 3 进行请求操作

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
                            ToastUtil.showToast("服务器异常，请稍后...");
                        }
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
                            ToastUtil.showToast("服务器异常，请稍后...");
                        }
                    }

                } catch (Exception exception) {
                    //  ToastUtil.showToast("微信获取数据异常");
                    finish();
                }

            }
        });

    }

}