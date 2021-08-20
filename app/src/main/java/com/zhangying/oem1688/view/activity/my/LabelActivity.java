package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xutil.data.DateUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.LabelAdpter;
import com.zhangying.oem1688.adpter.LabelRightAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.bean.MessageListBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseInterfacePosition;
import com.zhangying.oem1688.util.ToastUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LabelActivity extends BaseActivity {


    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.new_open_tv)
    TextView newOpenTv;
    @BindView(R.id.read_tv)
    TextView readTv;
    @BindView(R.id.unread_tv)
    TextView unreadTv;
    @BindView(R.id.screen_tv)
    TextView screenTv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.address_tv)
    TextView addressTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.recycview)
    MyRecycleView recycview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.recycview_popu)
    MyRecycleView recycviewPopu;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.end_time_tv)
    TextView endTimeTv;
    @BindView(R.id.address_et)
    EditText addressEt;
    @BindView(R.id.sure_tv)
    TextView sureTv;
    @BindView(R.id.clear_tv)
    TextView clearTv;
    @BindView(R.id.rootView_rl)
    RelativeLayout rootViewrl;
    private int page = 1;
    private int type = 2;
    private LabelAdpter labelAdpter;
    private LabelRightAdpter labelRightAdpter;
    private List<HomeBena.RetvalBean.SindustryBean> sindustry;
    private boolean isScreen = false;
    //行业编号
    private String cate_id = "";
    //开始时间
    private String start_time = "";
    //结束时间
    private String end_time = "";
    //输入的内容
    private String address = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_label;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("最新留言");
        labelAdpter = new LabelAdpter(this);
        WidgetUtils.initRecyclerView(recycview);
        recycview.setAdapter(labelAdpter);
        //右侧的选择
        WidgetUtils.initGridRecyclerView(recycviewPopu, 3, DensityUtils.dp2px(10));
        labelRightAdpter = new LabelRightAdpter();
        recycviewPopu.setAdapter(labelRightAdpter);

        labelRightAdpter.setBaseInterfacePosition(new BaseInterfacePosition() {
            @Override
            public void getPosition(int position, Boolean isboolean, View view) {
                for (int i = 0; i < sindustry.size(); i++) {
                    HomeBena.RetvalBean.SindustryBean sindustryBean = sindustry.get(i);
                    if (i == position) {
                        sindustryBean.setIsboolean(isboolean);
                    } else {
                        sindustryBean.setIsboolean(false);
                    }
                }
                labelRightAdpter.refresh(sindustry);
            }
        });

        statetv(type);
        initRefresh();
        gethome();
    }

    @OnClick({R.id.bacK_RL, R.id.new_open_tv, R.id.read_tv, R.id.unread_tv, R.id.screen_tv, R.id.start_time_tv, R.id.end_time_tv, R.id.sure_tv, R.id.clear_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.new_open_tv:  //0
                type = 0;
                statetv(0);
                break;
            case R.id.read_tv:  //1
                type = 1;
                statetv(1);
                break;
            case R.id.unread_tv:  //2
                type = 2;
                statetv(2);
                break;
            case R.id.screen_tv:
                isScreen = !isScreen;
                if (isScreen) {
                    screenTv.setText("关闭");
                    rootViewrl.setVisibility(View.VISIBLE);
                } else {
                    screenTv.setText("筛选");
                    rootViewrl.setVisibility(View.GONE);
                }

                break;
            case R.id.start_time_tv:
                showDatePicker(0);
                break;
            case R.id.end_time_tv:
                showDatePicker(1);
                break;
            case R.id.sure_tv:
                isScreen = !isScreen;
                screenTv.setText("筛选");
                rootViewrl.setVisibility(View.GONE);
                for (HomeBena.RetvalBean.SindustryBean sindustryBean : sindustry) {
                    if (sindustryBean.isIsboolean()) {
                        cate_id = sindustryBean.getCate_id();
                        break;
                    }
                }

                String mst = startTimeTv.getText().toString();
                String met = endTimeTv.getText().toString();
                String mat = addressEt.getText().toString();

                if (mst.equals("开始时间")) {
                    start_time = "";
                } else {
                    start_time = mst;
                }

                if (mst.equals("结束时间")) {
                    end_time = "";
                } else {
                    end_time = met;
                }

                if (mat == null || mat.length() == 0) {
                    address = "";
                } else {
                    address = mat;
                }


                myright();
                break;
            case R.id.clear_tv:
                startTimeTv.setText("开始时间");
                endTimeTv.setText("结束时间");
                addressEt.setText("");
                addressEt.setHint("请输入联系人地区关键词");
                for (int i = 0; i < sindustry.size(); i++) {
                    sindustry.get(i).setIsboolean(false);
                }
                labelRightAdpter.refresh(sindustry);

                cate_id = "";
                start_time = "";
                //结束时间
                end_time = "";
                //输入的内容
                address = "";
                break;
        }
    }

    //设置点击状态
    private void statetv(int type) {
        page = 1;
        originaltv();
        if (type == 0) {
            newOpenTv.setSelected(true);
        } else if (type == 1) {
            readTv.setSelected(true);
        } else if (type == 2) {
            unreadTv.setSelected(true);
        }
        myright();
    }

    private void originaltv() {
        newOpenTv.setSelected(false);
        readTv.setSelected(false);
        unreadTv.setSelected(false);
    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, LabelActivity.class);
        context.startActivity(intent);
    }

    private void myright() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("ly", "app");
        map.put("lytype", "newly"); //类型(gsly：公司咨询、newly：贴牌商库).
        map.put("storeid", "");  //店铺编号.
        map.put("gcateid", cate_id); //行业编号.
        map.put("dateBg", start_time); //开始时间.
        map.put("dateEd", end_time);//结束时间.
        map.put("status", type);//状态(0：最新留言、1：已查看、2：未查看).
        map.put("skey", address);//查询关键词.
        RemoteRepository.getInstance()
                .message_list(map)
                .subscribeWith(new DefaultDisposableSubscriber<MessageListBean>() {

                    @Override
                    protected void success(MessageListBean messageListBean) {
                        dissmissLoading();
                        List<MessageListBean.RetvalBean> retval = messageListBean.getRetval();
                        labelAdpter.setType(type);
                        if (page == 1) {
                            labelAdpter.refresh(retval);
                        } else {
                            labelAdpter.loadMore(retval);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    private void initRefresh() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                page++;
                myright();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                page = 1;
                myright();

            }
        });
    }


    private void showDatePicker(int mytype) {

        TimePickerView mDatePicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelected(Date date, View v) {
                String time = DateUtils.date2String(date, DateUtils.yyyyMMdd.get());
                if (mytype == 0) {
                    startTimeTv.setText(time);
                } else if (mytype == 1) {
                    endTimeTv.setText(time);
                }
            }
        })
                .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                .setTitleText("日期选择")
                .build();
        mDatePicker.show();
    }

    private void gethome() {
        RemoteRepository.getInstance()
                .gethome()
                .subscribeWith(new DefaultDisposableSubscriber<HomeBena>() {

                    @Override
                    protected void success(HomeBena data) {

                        HomeBena.RetvalBean retval = data.getRetval();
                        //tab
                        sindustry = retval.getSindustry();
                        labelRightAdpter.refresh(sindustry);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

}