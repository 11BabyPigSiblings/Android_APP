package com.goni99.smartlibraryadmin.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goni99.smartlibraryadmin.R

class BarcodeRecyclerViewHolder(
    itemView:View
):RecyclerView.ViewHolder(itemView) {
    val number = itemView.findViewById<TextView>(R.id.barcode_number_text_view)
    fun bind(num:String){
        number.text = num
    }
}