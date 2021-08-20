package com.zhangying.oem1688.view.activity.entry;
import android.app.Activity;
import com.xuexiang.xui.widget.activity.BaseGuideActivity;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class UserGuideActivity extends BaseGuideActivity {

    @Override
    protected List<Object> getGuideResourceList() {
        List<Object> list = new ArrayList<>();
        list.add(R.drawable.welcome1);
        list.add(R.drawable.welcome2);
        list.add(R.drawable.welcome3);
        return list;
    }

    @Override
    protected Class<? extends Activity> getSkipClass() {
        return MainActivity.class;
    }
}