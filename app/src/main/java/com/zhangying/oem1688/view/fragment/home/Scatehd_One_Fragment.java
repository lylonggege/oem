package com.zhangying.oem1688.view.fragment.home;

import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.GrideBean;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.custom.MyRecycleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Scatehd_One_Fragment extends BaseFragment {

    public Scatehd_One_Fragment(int positon) {
        this.positon = positon;
    }

    private int positon;

    @BindView(R.id.recycview)
    MyRecycleView recycview;



    private ArrayList<GrideBean> arrayList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scatehd__one_;
    }

    @Override
    public void initView() {
        WidgetUtils.initGridRecyclerView(recycview, 5, DensityUtils.dp2px(2));
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HomeBena(HomeBena homeBena) {
        if (homeBena != null) {

            //设置数据、
            if (homeBena.getRetval().getScatehd().size() > 0) {
                List<HomeBena.RetvalBean.ScatehdBean> scatehdBeans1 = homeBena.getRetval().getScatehd().get(positon);
                for (int i = 0; i < scatehdBeans1.size(); i++) {
                    HomeBena.RetvalBean.ScatehdBean scatehdBean = scatehdBeans1.get(i);
                    GrideBean grideBean = new GrideBean();
                    grideBean.setUrl(scatehdBean.getSpic());
                    grideBean.setName(scatehdBean.getSname());
                    arrayList.add(grideBean);
                }
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}