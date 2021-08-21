package com.zhangying.myapplication.shangxia

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhangying.oem1688.R


class SimpleAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var name_tv: TextView? = null
    var phone_tv: TextView? = null
    var content_tv: TextView? = null

    init {
        name_tv = itemView.findViewById(R.id.name_tv)
        phone_tv = itemView.findViewById(R.id.phone_tv)
        content_tv = itemView.findViewById(R.id.content_tv)
    }

}
