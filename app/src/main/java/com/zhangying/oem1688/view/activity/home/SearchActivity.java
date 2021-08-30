package com.zhangying.oem1688.view.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.FlowTagAdapter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.db.DbDao;
import com.zhangying.oem1688.util.AppManagerUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.et_input)
    EditText et_input;
    @BindView(R.id.flowlayout_normal_select)
    FlowTagLayout mNormalFlowTagLayout;
    private ArrayList<String> tagArrayList = new ArrayList<>();
    private DbDao dbDao;
    private FlowTagAdapter tagAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化获取焦点
        et_input.requestFocus();

        AppManagerUtil.getInstance().addHomeActivity(this);
        dbDao = new DbDao(this);
        query();
        tagAdapter = new FlowTagAdapter(SearchActivity.this);
        mNormalFlowTagLayout.setAdapter(tagAdapter);
        mNormalFlowTagLayout.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                SearchResultActivity.simpleActivity(SearchActivity.this, (String) parent.getAdapter().getItem(position));
                finish();
            }
        });
        tagAdapter.addTags(tagArrayList);
        et_input.setOnEditorActionListener(this);

    }

    private void query() {
        tagArrayList.clear();
        List<String> strings = dbDao.queryData("");
        for (int i = 0; i < strings.size(); i++) {
            tagArrayList.add(strings.get(i));
        }
    }

    @OnClick({R.id.et_input, R.id.clear_ll, R.id.imageView5, R.id.imageView2,R.id.imageView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                finish();
                break;
            case R.id.clear_ll:
                DialogLoader.getInstance().showConfirmDialog(
                        SearchActivity.this,
                        getString(R.string.clean_history_record),
                        getString(R.string.lab_yes),
                        (dialog, which) -> {
                            dbDao.deleteData();
                            query();
                            tagAdapter.clearAndAddTags(tagArrayList);
                            tagAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        },
                        getString(R.string.lab_no),
                        (dialog, which) -> {
                            dialog.dismiss();
                        }
                );
                break;
            case R.id.imageView2:
            case R.id.imageView5:
                String searchStr = et_input.getText().toString();
                dbDao.insertData(searchStr);
                query();
                tagAdapter.clearAndAddTags(tagArrayList);
                SearchResultActivity.simpleActivity(SearchActivity.this, searchStr);
                finish();
                break;

        }
    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String searchStr = et_input.getText().toString();
            dbDao.insertData(searchStr);
            query();
            tagAdapter.clearAndAddTags(tagArrayList);
            SearchResultActivity.simpleActivity(SearchActivity.this, searchStr);
            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManagerUtil.getInstance().finishhomeActivity(this);
    }
}