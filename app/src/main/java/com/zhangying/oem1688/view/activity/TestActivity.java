package com.zhangying.oem1688.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.db.StuDBHelper;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.util.ToastUtil;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;


public class TestActivity extends AppCompatActivity {
    //声明各个按钮
    private Button createButton;
    private Button insertButton;
    private Button updateButton;
    private Button queryBtn;
    private Button deleteBtn;
    private Button ModifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SQLiteStudioService.instance().start(this);//使用sqllite 调试数据库
        setContentView(R.layout.activity_test);
        //初始化界面
        initView();
        //监听事件
        setListener();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        createButton = findViewById(R.id.createDatabase);
        insertButton = findViewById(R.id.insert);
        updateButton = findViewById(R.id.updateDatabas);
        queryBtn = findViewById(R.id.query);
        deleteBtn = findViewById(R.id.delete);
        ModifyBtn = findViewById(R.id.update);
    }

    /**
     * 监听事件
     */
    private void setListener() {
        createButton.setOnClickListener(new CreateListener());
        updateButton.setOnClickListener(new UpdateListener());
        insertButton.setOnClickListener(new InsertListener());
        ModifyBtn.setOnClickListener(new ModifyListener());
        queryBtn.setOnClickListener(new QueryListener());
        deleteBtn.setOnClickListener(new DeleteListener());
    }

    /**
     * 数据库的创建
     */
    private class CreateListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //创建StuDBHelper对象

            StuDBHelper dbHelper = new StuDBHelper(TestActivity.this, "stu_db", null, 1);
            //得到一个可读的SQLiteDatabase对象
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Toast.makeText(TestActivity.this, "创建数据库成功", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * 数据的插入
     */
    private class InsertListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            StuDBHelper dbHelper = new StuDBHelper(TestActivity.this, "stu_db", null, 1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //插入数据SQL语句
            String sql = "insert into stu_table(id,sname,sage,ssex) values(10086,'张三',14,'女')";//插入单条数据
            //String sql = "INSERT INTO stu_table(id,sname,sage,ssex) SELECT 2,'liming',28,'male' UNION ALL SELECT 3,'wanghong',29,'male'";插入多条数据

            //执行SQL语句
            db.execSQL(sql);
            Toast.makeText(TestActivity.this, "插入数据成功!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 数据的删除
     */
    private class DeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            StuDBHelper dbHelper = new StuDBHelper(TestActivity.this, "stu_db", null, 1);
            //得到一个可写的数据库
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //删除SQL语句
            String sql1 = "delete from stu_table where id =1";
            //执行SQL语句
            db.execSQL(sql1);
            Toast.makeText(TestActivity.this, "删除数据成功!", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * 数据的更新
     */
    private class ModifyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            StuDBHelper dbHelper = new StuDBHelper(TestActivity.this, "stu_db", null, 1);
            //得到一个可写的数据库
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //修改SQL语句
            String sql = "update stu_table set sname = 'djp' where id = 1";
            //执行SQL
            db.execSQL(sql);
            Toast.makeText(TestActivity.this, "数据库更新成功", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * 数据库版本的更新
     */
    private class UpdateListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 数据库版本的更新,由原来的1变为2
            StuDBHelper dbHelper = new StuDBHelper(TestActivity.this, "stu_db", null, 2);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Toast.makeText(TestActivity.this, "数据库版本更新成功", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * 数据的查询
     */
    private class QueryListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            StuDBHelper dbHelper = new StuDBHelper(TestActivity.this, "stu_db", null, 1);
            //得到一个可写的数据库
            SQLiteDatabase db = dbHelper.getReadableDatabase();


            //参数1：表名
            //参数2：要想显示的列
            //参数3：where子句
            //参数4：where子句对应的条件值
            //参数5：分组方式
            //参数6：having条件
            //参数7：排序方式

            Cursor cursor = db.query("stu_table", new String[]{"id", "sname", "sage", "ssex"}, "id=1 and sage=23", null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("sname"));
                String age = cursor.getString(cursor.getColumnIndex("sage"));
                String sex = cursor.getString(cursor.getColumnIndex("ssex"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                System.out.println("查询------->" + "ID-----" + id + "姓名：" + name + " " + "年龄：" + age + " " + "性别：" + sex);
            }
            //关闭数据库
            db.close();
        }
    }
}