package com.zhangying.myapplication.shangxia

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhangying.oem1688.R
import com.zhangying.oem1688.bean.HomeBena


class SimpleAdapter(list: MutableList<HomeBena.RetvalBean.ScinfolistBean>, mContext: Context) : RecyclerView.Adapter<SimpleAdapterViewHolder>() {

    /**数据源*/
    private var list: MutableList<HomeBena.RetvalBean.ScinfolistBean> = list

    /**上下文*/
    private var mContext: Context = mContext

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SimpleAdapterViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.autopollrecyclerviewitem, p0, false)
        return SimpleAdapterViewHolder(view)
    }

    /**
     * 为了实现无线循环，需要将数据的源的个数设置为比较大的值
     */
    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(p0: SimpleAdapterViewHolder, p1: Int) {
        p0.name_tv?.text = list!![p1 % list!!.size].name
        p0.phone_tv?.text = list!![p1 % list!!.size].phone
        p0.content_tv?.text = list!![p1 % list!!.size].title
    }
}
