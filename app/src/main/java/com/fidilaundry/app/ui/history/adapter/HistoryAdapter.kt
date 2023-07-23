package com.fidilaundry.app.ui.history.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fidilaundry.app.ui.history.model.HistoryData
import com.fidilaundry.app.R
import com.fidilaundry.app.databinding.ItemHistoryBinding
import com.fidilaundry.app.model.response.OrderListResponse
import com.fidilaundry.app.util.DateTimeFormater
import kotlin.math.roundToInt

class HistoryAdapter(private val context: Context?) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var appList: List<OrderListResponse.Result>

    fun updateList(appList: List<OrderListResponse.Result>) {
        this.appList = appList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val app = appList[position]

            binding.tvType.text = app.serviceID.toString()
            binding.tvDate.text = DateTimeFormater(app.createdAt!!)
            binding.tvStatus.text = app.status
//            binding.tvTotal.text = app.total.toString()

        }
    }

    init {
        appList = ArrayList()
    }
}