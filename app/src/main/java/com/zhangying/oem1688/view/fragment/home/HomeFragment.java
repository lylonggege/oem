package com.zhangying.oem1688.view.fragment.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.adapter.recyclerview.XGridLayoutManager;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.textview.marqueen.MarqueeFactory;
import com.xuexiang.xui.widget.textview.marqueen.MarqueeView;
import com.xuexiang.xui.widget.textview.marqueen.SimpleNoticeMF;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.HomeGoodAdpter;
import com.zhangying.oem1688.adpter.HomeSzhshAdpter;
import com.zhangying.oem1688.adpter.ScatehdMenuAdpter;
import com.zhangying.oem1688.adpter.ViewPagerAdapter;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.bean.RecomendIndexBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.custom.VerticalScrolledListview;
import com.zhangying.oem1688.custom.WrapContentHeightViewPager;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.SpacesItemDecoration;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.view.activity.home.FindFactoryActivity;
import com.zhangying.oem1688.view.activity.home.FindProductActivity;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    private static final String KEY_TITLE = "title";
    @BindView(R.id.findfactory_rl)
    RelativeLayout findfactoryRl;
    @BindView(R.id.findproduct_rl)
    RelativeLayout findproductRl;
    private String cate_id;

    @BindView(R.id.myRecycleView_gcates)
    MyRecycleView myRecycleView_gcates;
    @BindView(R.id.pageLine1)
    View pageLine1;
    @BindView(R.id.pageLine2)
    View pageLine2;

    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.news_tv)
    TextView newsTv;
    @BindView(R.id.recycView_szhshlist)
    MyRecycleView recycView_szhshlist;
    @BindView(R.id.recycView_sgoodlist)
    MyRecycleView recycView_sgoodlist;
    @BindView(R.id.more_name_tv)
    TextView more_name_tv;
    @BindView(R.id.lunbo_RL)
    RelativeLayout lunbo_RL;
    @BindView(R.id.queryfactory_ll)
    LinearLayout queryfactory_ll;
    @BindView(R.id.banner)
    MZBannerView bannerView;
    @BindView(R.id.marqueeView_LL)
    LinearLayout marqueeView_LL;
    @BindView(R.id.line_container_tv)
    RelativeLayout line_container_tv;
    @BindView(R.id.view_bg)
    View view_bg;
    @BindView(R.id.help_bold_1)
    TextView help_bold_1;
    @BindView(R.id.oem_bold_1)
    TextView oem_bold_1;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<String> marqueeViewList = new ArrayList<>();
    private HomeSzhshAdpter home_szhshAdpter;
    private HomeGoodAdpter home_goodAdpter;

    @Override
    protected int getLayoutId() {
        return R.layout.simpletabfragment;
    }

    @Override
    public void initView() {

        //设置标题“帮我找工厂、承接代加工”加粗度
        float boldRate = 1.2f;
        help_bold_1.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        help_bold_1.getPaint().setStrokeWidth(boldRate);

        oem_bold_1.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        oem_bold_1.getPaint().setStrokeWidth(boldRate);

        cate_id = getArguments().getString(KEY_TITLE);
        /**
         * -999 代表行业  显示“帮我找工厂、承接代加工”  首页
         */
        if (cate_id.equals("-999")) {
            //发布信息上下滚动字幕
            lunbo_RL.setVisibility(View.VISIBLE);
            //帮我找工厂、承接代加工区域
            queryfactory_ll.setVisibility(View.VISIBLE);
            //头条区域上下跑马灯
            marqueeView_LL.setVisibility(View.VISIBLE);
            //板块之间分割区域
            view_bg.setVisibility(View.VISIBLE);
            HashMapSingleton instance = HashMapSingleton.getInstance();
            instance.put("ly", "app");
            gethome(instance, 0);
        } else {
            //字体轮播布局隐藏
            lunbo_RL.setVisibility(View.GONE);
            marqueeView_LL.setVisibility(View.GONE);
            queryfactory_ll.setVisibility(View.GONE);
            HashMapSingleton instance = HashMapSingleton.getInstance();
            instance.put("ly", "app");
            instance.put("type", cate_id);
            getrecomendindex(instance, 1);
            view_bg.setVisibility(View.GONE);
        }
        init();
    }

    /**
     * @param cate_id 首页的cate_id  -999   推荐的是分类id  根据不同的分类ID请求不同的接口
     * @return
     */
    public static HomeFragment newInstance(String cate_id) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, cate_id);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 初始化顶部产品分类板块
     * @param data 产品分类数据集合
     */
    private void initCateFragment(List<List<HomeBena.RetvalBean.ScatehdBean>> data){
        if (data.size() < 0) {
            return;
        }

        int columns = 5;
        int screenWidth = ScreenTools.instance(getActivity()).getScreenWidth();
        int itemW = screenWidth / columns;

        List<HomeBena.RetvalBean.ScatehdBean> firstScatehdBeans = data.get(0);
        int firstNum = firstScatehdBeans.size();
        if (firstNum < columns)itemW = screenWidth / firstNum;//若单行不足5个，则重新计算每一项宽度
        int pages = (int) Math.ceil((float)firstScatehdBeans.size() / (float)columns);  //总页数
        if (pages == 1){//只有一页则隐藏分页指示器
            if (line_container_tv.getVisibility() != View.GONE) {
                line_container_tv.setVisibility(View.GONE);
            }
        }else {
            if (line_container_tv.getVisibility() != View.VISIBLE) {
                line_container_tv.setVisibility(View.VISIBLE);
            }
        }

        int pageTotalW = pageLine1.getWidth();//页码总数指示器长度

        //获取滚动页码的布局
        RelativeLayout.LayoutParams rlp2 = (RelativeLayout.LayoutParams) pageLine2.getLayoutParams();
        rlp2.width = pageTotalW / pages;
        pageLine2.setLayoutParams(rlp2);

        List<HomeBena.RetvalBean.ScatehdBean> scatehdBeansList = new ArrayList<>();
        //数据排序,接口返回的第一行显示客户端第一行，第二行显示再客户端第二行
        List<HomeBena.RetvalBean.ScatehdBean> secondScatehdBeans = data.get(1);
        int secondNum = secondScatehdBeans.size();
        int maxNum = Math.max(firstNum,secondNum);//获取上下两行中数量最多的一行分类
        int itemTotalW = maxNum * itemW;//产品分类总宽度
        for (int i1 = 0; i1 < maxNum; i1++) {
            if (i1 < firstNum){
                scatehdBeansList.add(firstScatehdBeans.get(i1));
            }

            if (i1 < secondNum){
                scatehdBeansList.add(secondScatehdBeans.get(i1));
            }
        }

        //循环显示推荐的商品分类
        ScatehdMenuAdpter scathedAdapter = new ScatehdMenuAdpter(getActivity(),itemW);
        scathedAdapter.refresh(scatehdBeansList);

        //设置横向滚动布局、显示2行
        int spanCount = 2;
        XGridLayoutManager xgridLayout = new XGridLayoutManager(myRecycleView_gcates.getContext(), spanCount);
        xgridLayout.setOrientation(RecyclerView.HORIZONTAL);
        xgridLayout.setScrollEnabled(true);
        myRecycleView_gcates.setLayoutManager(xgridLayout);
        myRecycleView_gcates.addItemDecoration(new GridDividerItemDecoration(myRecycleView_gcates.getContext(), spanCount, DensityUtils.dp2px(0)));
        myRecycleView_gcates.setItemAnimator(new DefaultItemAnimator());
        myRecycleView_gcates.setAdapter(scathedAdapter);
        myRecycleView_gcates.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int mmRvScrollX = 0; // 列表滑动距离
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //方式1：获取x方向滚动距离--------------不能准确
//                LinearLayoutManager layoutManager = (LinearLayoutManager) myRecycleView_gcates.getLayoutManager();
//                int position = layoutManager.findFirstVisibleItemPosition();
//                View firstVisiableChildView = layoutManager.findViewByPosition(position);
//                int itemWidth = firstVisiableChildView.getWidth();
//                int baseX = (position) * itemWidth - firstVisiableChildView.getLeft();

                //方式2：获取x方向滚动距离
                mmRvScrollX += dx;

                //设置分页视图的滚动位置
                RelativeLayout.LayoutParams rlp2 = (RelativeLayout.LayoutParams) pageLine2.getLayoutParams();
                float rate = (float) mmRvScrollX / (float)(itemTotalW - screenWidth);
                rlp2.leftMargin = (int)(rate * (pageLine1.getWidth() - pageLine2.getWidth()));
                pageLine2.setLayoutParams(rlp2);
            }
        });
    }

    private void init() {
        viewPagerAdapter = new ViewPagerAdapter();
        home_szhshAdpter = new HomeSzhshAdpter(getActivity());
        home_goodAdpter = new HomeGoodAdpter(getActivity());
    }

    private void gethome(HashMapSingleton instance, int type) {
        RemoteRepository.getInstance()
                .gethome()
                .subscribeWith(new DefaultDisposableSubscriber<HomeBena>() {

                    @Override
                    protected void success(HomeBena data) {

                        HomeBena.RetvalBean retval = data.getRetval();
                        //tab
                        List<HomeBena.RetvalBean.SindustryBean> sindustry = retval.getSindustry();
                        //banner
                        List<HomeBena.RetvalBean.SbannerBean> sbanner = retval.getSbanner();
                        //头条 文字滚动
                        HomeBena.RetvalBean.SnewslistBean snewslistBean = retval.getSnewslist();
                        //zhshlist
                        List<HomeBena.RetvalBean.SzhshlistBean> szhshlist = retval.getSzhshlist();

                        //tab
//                        initTab(sindustry);
                        //banner
                        initbanner(sbanner);
                        //初始化fragment
                        //initfragment(data.getRetval().getScatehd());
                        initCateFragment(data.getRetval().getScatehd());
                        //文字滚动
                        initmarqueeView(snewslistBean);
                        //多条文字轮播
                        initScinfolist(retval.getScinfolist());
                        //列表
                        initszhshlist(szhshlist, type);
                        //加载更多
                        more_name_tv.setText(retval.getSgoodsList().getGtitle());
                        initgoosList(retval.getSgoodsList());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    private void getrecomendindex(HashMapSingleton instance, int type) {

        RemoteRepository.getInstance()
                .getrecomendindex(instance)
                .subscribeWith(new DefaultDisposableSubscriber<RecomendIndexBean>() {
                    @Override
                    protected void success(RecomendIndexBean data) {
                        RecomendIndexBean.RetvalBean retval = data.getRetval();
                        //banner
                        List<HomeBena.RetvalBean.SbannerBean> sbanner = retval.getSbanner();
                        //首页的招商列表与推荐推荐的推荐专区
                        List<HomeBena.RetvalBean.SzhshlistBean> szhshlist = retval.getSzhshlist();
                        //banner
                        initbanner(sbanner);
                        //初始化fragment
                        //initfragment(data.getRetval().getScatehd());
                        initCateFragment(data.getRetval().getScatehd());
                        //列表
                        initszhshlist(szhshlist, type);
                        //加载更多
                        more_name_tv.setText(retval.getGtitle());
                        //initgoosList(retval.getSgoodsList());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    private void initScinfolist(List<HomeBena.RetvalBean.ScinfolistBean> scinfolist) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < scinfolist.size(); i++) {
            HomeBena.RetvalBean.ScinfolistBean scinfolistBean = scinfolist.get(i);
            try {
                String name = strAppendStr(scinfolistBean.getName(), 8, "  ");
                strings.add(name + scinfolistBean.getPhone() + "      " + scinfolistBean.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        VerticalScrolledListview verticalScrolledListview = new VerticalScrolledListview(context);
        verticalScrolledListview.setData(strings);
        lunbo_RL.addView(verticalScrolledListview);
    }

    private void initgoosList(HomeBena.RetvalBean.SgoodsListBean sgoodsList) {
        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
        recycView_sgoodlist.addItemDecoration(new SpacesItemDecoration(space, space));
        recycView_sgoodlist.setLayoutManager(new GridLayoutManager(context, 2));
        home_goodAdpter.refresh(sgoodsList.getGoods());
        recycView_sgoodlist.setAdapter(home_goodAdpter);
    }

    private void initszhshlist(List<HomeBena.RetvalBean.SzhshlistBean> szhshlist, int type) {
        recycView_szhshlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        home_szhshAdpter.refresh(szhshlist);
        recycView_szhshlist.setAdapter(home_szhshAdpter);
    }

    private void initmarqueeView(HomeBena.RetvalBean.SnewslistBean bean) {
        newsTv.setText(bean.getNtitle());
        marqueeViewList.clear();
        for (int i = 0; i < bean.getNlist().size(); i++) {
            HomeBena.RetvalBean.SnewslistBean.NlistBean nlistBean = bean.getNlist().get(i);
            marqueeViewList.add(nlistBean.getNtitle());
        }

        MarqueeFactory<TextView, String> marqueeFactory1 = new SimpleNoticeMF(getContext());
        marqueeFactory1.setData(marqueeViewList);
        marqueeView.setMarqueeFactory(marqueeFactory1);
        marqueeFactory1.setOnItemClickListener((view, holder) -> ToastUtil.showToast(holder.getData()));
        marqueeView.startFlipping();
    }

    private void initbanner(List<HomeBena.RetvalBean.SbannerBean> sbanner) {

        try {
            /**
             * 图片轮播的简单使用
             */

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) bannerView.getLayoutParams();
            layoutParams.width = ScreenTools.instance(context).getScreenWidth() - ScreenTools.instance(context).dip2px(20);
            layoutParams.height = layoutParams.width * 260 / 720;
            bannerView.setLayoutParams(layoutParams);

            bannerView.setPages(sbanner, new MZHolderCreator<BannerViewHolder>() {
                @Override
                public BannerViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });
        } catch (Exception e) {

        }


    }


    /**
     * 设置字符长度  不足者 右侧添加 指定字符
     *
     * @param str1  元字符
     * @param lenth 指定长度
     * @param st2   指定字符
     * @return
     * @throws Exception
     */
    public static String strAppendStr(String str1, int lenth, String st2) throws Exception {
        StringBuilder strb1 = new StringBuilder(str1);
        lenth = lenth - getChineseLength(str1, "utf-8");
        while (lenth >= 0) {
            lenth--;
            strb1.append(st2);
        }
        return strb1.toString();
    }

    /**
     * 计算中文字符长度
     *
     * @param name      字符
     * @param endcoding 编码方式
     * @return
     * @throws Exception
     */
    public static int getChineseLength(String name, String endcoding) throws Exception {
        int len = 0; //定义返回的字符串长度
        int j = 0;
        //按照指定编码得到byte[]
        byte[] b_name = name.getBytes(endcoding);
        do {
            short tmpst = (short) (b_name[j] & 0xF0);
            if (tmpst >= 0xB0) {
                if (tmpst < 0xC0) {
                    j += 2;
                    len += 2;
                } else if ((tmpst == 0xC0) || (tmpst == 0xD0)) {
                    j += 2;
                    len += 2;
                } else if (tmpst == 0xE0) {
                    j += 3;
                    len += 2;
                } else {
                    short tmpst0 = (short) (((short) b_name[j]) & 0x0F);
                    if (tmpst0 == 0) {
                        j += 4;
                        len += 2;
                    } else if (tmpst0 < 12) {
                        j += 5;
                        len += 2;
                    } else {
                        j += 6;
                        len += 2;
                    }
                }
            } else {
                j += 1;
                len += 1;
            }
        } while (j <= b_name.length - 1);
        return len;
    }

    @OnClick({R.id.findfactory_rl, R.id.findproduct_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.findfactory_rl:
                FindFactoryActivity.simpleActivity(getActivity());
                break;
            case R.id.findproduct_rl:
                FindProductActivity.simpleActivity(getActivity());
                break;
        }
    }

    public static class BannerViewHolder implements MZViewHolder<HomeBena.RetvalBean.SbannerBean> {
        private RadiusImageView mImageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (RadiusImageView) view.findViewById(R.id.banner_image);
            mImageView.setCornerRadius(10);
            return view;
        }

        @Override
        public void onBind(Context context, int position, HomeBena.RetvalBean.SbannerBean data) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background) //占位图
                    .error(R.drawable.ic_launcher_background)      //错误图
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(data.getAd_logo()).apply(options).into(mImageView);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerView.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerView.start();//开始轮播
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
