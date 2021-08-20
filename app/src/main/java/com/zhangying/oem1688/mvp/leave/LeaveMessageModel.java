package com.zhangying.oem1688.mvp.leave;

import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.GoodsdetailBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseFinishListener;
import com.zhangying.oem1688.onterface.BaseModel;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.ToastUtil;

import java.util.HashMap;

public class LeaveMessageModel implements BaseModel {

    private DateBean dateBean;

    public LeaveMessageModel(DateBean dateBean) {
        this.dateBean = dateBean;
    }

    //请求数据
    @Override
    public void getData(BaseFinishListener baseFinishListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uname", dateBean.getName());
        map.put("utel", dateBean.getPhone());
        //公司编号
        map.put("lycomId", dateBean.getLycomId());
        map.put("lycate", dateBean.getLycate());
        map.put("lylm", dateBean.getLylm());
        //浏览栏目编号(产品编号、公司编号、新闻编号、需求编号
        map.put("id", dateBean.getId());
        map.put("lyagent", dateBean.getLyagent());
        RemoteRepository.getInstance()
                .add_message(map)
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                    @Override
                    protected void success(BaseBean data) {
                        baseFinishListener.success(data);
                        baseFinishListener.hidendloading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        baseFinishListener.hidendloading();
                    }
                });
    }
}
