package com.zhangying.oem1688.view.fragment.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.adapter.recyclerview.XGridLayoutManager;
import com.xuexiang.xui.utils.DensityUtils;
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
import com.zhangying.oem1688.bean.HomeGoodsBean;
import com.zhangying.oem1688.bean.RecomendIndexBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.custom.VerticalScrolledListview;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.GlobalEntitySingleton;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.FlowSpaceItemDecoration;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.SpacesItemDecoration;
import com.zhangying.oem1688.view.activity.home.FindFactoryActivity;
import com.zhangying.oem1688.view.activity.home.FindProductActivity;
import com.zhangying.oem1688.view.activity.home.NewsDetailActivity;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
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
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.simpletabfragment;
    }

    @Override
    public void initView() {
        init();

        //????????????????????????????????????????????????????????????
        float boldRate = 1.2f;
        help_bold_1.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        help_bold_1.getPaint().setStrokeWidth(boldRate);

        oem_bold_1.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        oem_bold_1.getPaint().setStrokeWidth(boldRate);

        cate_id = getArguments().getString(KEY_TITLE);
        /**
         * -999 ????????????  ?????????????????????????????????????????????  ??????
         */
        if (cate_id.equals("-999")) {
            //??????????????????????????????
            lunbo_RL.setVisibility(View.VISIBLE);
            //???????????????????????????????????????
            queryfactory_ll.setVisibility(View.VISIBLE);
            //???????????????????????????
            marqueeView_LL.setVisibility(View.VISIBLE);
            //????????????????????????
            view_bg.setVisibility(View.VISIBLE);
            HashMapSingleton instance = HashMapSingleton.getInstance();
            instance.put("ly", "app");
            gethome(instance, 0);
        } else {
            page = 1;
            //????????????????????????
            lunbo_RL.setVisibility(View.GONE);
            marqueeView_LL.setVisibility(View.GONE);
            queryfactory_ll.setVisibility(View.GONE);
            HashMapSingleton instance = HashMapSingleton.getInstance();
            instance.put("ly", "app");
            instance.put("type", cate_id);
            getrecomendindex(instance, 1);
            view_bg.setVisibility(View.GONE);
        }
        //?????????????????????
        initRefresh();
    }

    /**
     * @param cate_id ?????????cate_id  -999   ??????????????????id  ?????????????????????ID?????????????????????
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
     * ?????????????????????????????????
     * @param data ????????????????????????
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
        if (firstNum < columns)itemW = screenWidth / firstNum;//???????????????5????????????????????????????????????
        int pages = (int) Math.ceil((float)firstScatehdBeans.size() / (float)columns);  //?????????
        if (pages == 1){//????????????????????????????????????
            if (line_container_tv.getVisibility() != View.GONE) {
                line_container_tv.setVisibility(View.GONE);
            }
        }else {
            if (line_container_tv.getVisibility() != View.VISIBLE) {
                line_container_tv.setVisibility(View.VISIBLE);
            }
        }

        //int pageTotalW = pageLine1.getWidth();//???????????????????????????
        int pageTotalW = 240;//???????????????????????????
        RelativeLayout.LayoutParams rlp1 = (RelativeLayout.LayoutParams) pageLine1.getLayoutParams();
        rlp1.width = pageTotalW;
        pageLine1.setLayoutParams(rlp1);

        //???????????????????????????
        RelativeLayout.LayoutParams rlp2 = (RelativeLayout.LayoutParams) pageLine2.getLayoutParams();
        rlp2.width = pageTotalW / pages;
        System.out.println("cateid:"+cate_id);
        System.out.println("pageTotalW:"+pageTotalW);
        System.out.println("pages:"+pages);
        pageLine2.setLayoutParams(rlp2);

        List<HomeBena.RetvalBean.ScatehdBean> scatehdBeansList = new ArrayList<>();
        //????????????,???????????????????????????????????????????????????????????????????????????????????????
        List<HomeBena.RetvalBean.ScatehdBean> secondScatehdBeans = data.get(1);
        int secondNum = secondScatehdBeans.size();
        int maxNum = Math.max(firstNum,secondNum);//????????????????????????????????????????????????
        int itemTotalW = maxNum * itemW;//?????????????????????
        for (int i1 = 0; i1 < maxNum; i1++) {
            if (i1 < firstNum){
                scatehdBeansList.add(firstScatehdBeans.get(i1));
            }

            if (i1 < secondNum){
                scatehdBeansList.add(secondScatehdBeans.get(i1));
            }
        }

        //?????????????????????????????????
        ScatehdMenuAdpter scathedAdapter = new ScatehdMenuAdpter(getActivity(),itemW);
        scathedAdapter.refresh(scatehdBeansList);

        //?????????????????????????????????2???
        int spanCount = 2;
        XGridLayoutManager xgridLayout = new XGridLayoutManager(myRecycleView_gcates.getContext(), spanCount);
        xgridLayout.setOrientation(RecyclerView.HORIZONTAL);
        xgridLayout.setScrollEnabled(true);
        myRecycleView_gcates.setLayoutManager(xgridLayout);
        myRecycleView_gcates.addItemDecoration(new GridDividerItemDecoration(myRecycleView_gcates.getContext(), spanCount, DensityUtils.dp2px(0)));
        myRecycleView_gcates.setItemAnimator(new DefaultItemAnimator());
        myRecycleView_gcates.setAdapter(scathedAdapter);
        myRecycleView_gcates.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int mmRvScrollX = 0; // ??????????????????
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //??????1?????????x??????????????????--------------????????????
//                LinearLayoutManager layoutManager = (LinearLayoutManager) myRecycleView_gcates.getLayoutManager();
//                int position = layoutManager.findFirstVisibleItemPosition();
//                View firstVisiableChildView = layoutManager.findViewByPosition(position);
//                int itemWidth = firstVisiableChildView.getWidth();
//                int baseX = (position) * itemWidth - firstVisiableChildView.getLeft();

                //??????2?????????x??????????????????
                mmRvScrollX += dx;

                //?????????????????????????????????
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
        HomeBena homeEntity = GlobalEntitySingleton.getInstance().getHomeData();
        if (homeEntity != null){
            renderHomeIndex(homeEntity, type);
            return;
        }

        //????????????
        RemoteRepository.getInstance()
                .gethome()
                .subscribeWith(new DefaultDisposableSubscriber<HomeBena>() {

                    @Override
                    protected void success(HomeBena data) {
                        renderHomeIndex(data, type);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    private void renderHomeIndex(HomeBena homeEntity, int type){
        HomeBena.RetvalBean retval = homeEntity.getRetval();
        //???????????????
        List<HomeBena.RetvalBean.SbannerBean> sbanner = retval.getSbanner();
        initbanner(sbanner);

        //??????????????????
        HomeBena.RetvalBean.SnewslistBean snewslistBean = retval.getSnewslist();
        initmarqueeView(snewslistBean);

        //???????????????
        List<HomeBena.RetvalBean.SzhshlistBean> szhshlist = retval.getSzhshlist();
        initszhshlist(szhshlist, type);

        //?????????fragment
        initCateFragment(homeEntity.getRetval().getScatehd());
        //??????????????????
        initScinfolist(retval.getScinfolist());

        //????????????
        more_name_tv.setText(retval.getSgoodsList().getGtitle());
        //initgoosList(retval.getSgoodsList());
        bindGoodsAdapter();
        loadMoreGoods();
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
                        initbanner(sbanner);

                        //???????????????????????????????????????????????????
                        List<HomeBena.RetvalBean.SzhshlistBean> szhshlist = retval.getSzhshlist();
                        initszhshlist(szhshlist, type);

                        //?????????fragment
                        initCateFragment(data.getRetval().getScatehd());

                        //????????????
                        more_name_tv.setText(retval.getGtitle());

                        bindGoodsAdapter();
                        loadMoreGoods();
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
                //String name = strAppendStr(scinfolistBean.getName(), 8, "  ");
                String name = scinfolistBean.getName();
                strings.add(name + "("+scinfolistBean.getPhone() + ")," + scinfolistBean.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        VerticalScrolledListview verticalScrolledListview = new VerticalScrolledListview(context);
        verticalScrolledListview.setOnItemClickListener(new VerticalScrolledListview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        verticalScrolledListview.setPadding(0,10,0,10);
        verticalScrolledListview.setData(strings);
        lunbo_RL.addView(verticalScrolledListview);
    }

    private void initgoosList(HomeBena.RetvalBean.SgoodsListBean sgoodsList) {
        bindGoodsAdapter();
        home_goodAdpter.refresh(sgoodsList.getGoods());
    }

    private void bindGoodsAdapter(){
        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
        //recycView_sgoodlist.addItemDecoration(new SpacesItemDecoration(space, space));
        //recycView_sgoodlist.setLayoutManager(new GridLayoutManager(context, 2));
        recycView_sgoodlist.addItemDecoration(new FlowSpaceItemDecoration(space, space));
        recycView_sgoodlist.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
        marqueeFactory1.setOnItemClickListener((view, holder) -> NewsDetailActivity.simpleActivity(getActivity(),bean.getNlist().get(holder.getPosition()).getNid(), 1));
        marqueeView.startFlipping();

        List<TextView> mViews = marqueeFactory1.getMarqueeViews();
        for (TextView tx:mViews){
            tx.setTextSize(14);
            tx.setTextColor(Color.parseColor("#666666"));
        }
    }

    private void initbanner(List<HomeBena.RetvalBean.SbannerBean> sbanner) {
        try {
            /**
             * ???????????????????????????
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

    //?????????????????????
    private void initRefresh() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                loadMoreGoods();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                loadMoreGoods();
                refreshLayout.finishRefresh();
            }
        });
    }

    //??????????????????
    private void loadMoreGoods() {
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("page", page);
        HashMapSingleton.getInstance().put("recomid", "999");
        if (!cate_id.equals("-999")) {
            HashMapSingleton.getInstance().put("cateid", cate_id);
        }
        RemoteRepository.getInstance()
                .home_goods_more(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<HomeGoodsBean>() {

                    @Override
                    protected void success(HomeGoodsBean data) {
                        System.out.println("loadMoreGoods2");
                        List<HomeBena.RetvalBean.SgoodsListBean.GoodsBean> recordList = data.getRetval();
                        if (page == 1) {
                            home_goodAdpter.refresh(recordList);
                        } else {
                            home_goodAdpter.loadMore(recordList);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        System.out.println("loadMoreGoods3");
                    }
                });
    }

    /**
     * ??????????????????  ????????? ???????????? ????????????
     *
     * @param str1  ?????????
     * @param lenth ????????????
     * @param st2   ????????????
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
     * ????????????????????????
     *
     * @param name      ??????
     * @param endcoding ????????????
     * @return
     * @throws Exception
     */
    public static int getChineseLength(String name, String endcoding) throws Exception {
        int len = 0; //??????????????????????????????
        int j = 0;
        //????????????????????????byte[]
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
        if (!AppUtils.isFastClick()){
            return;
        }
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
            mImageView.setCornerRadius(20);
            return view;
        }

        @Override
        public void onBind(Context context, int position, HomeBena.RetvalBean.SbannerBean data) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background) //?????????
                    .error(R.drawable.ic_launcher_background)      //?????????
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(data.getAd_logo()).apply(options).into(mImageView);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerView.pause();//????????????
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerView.start();//????????????
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}