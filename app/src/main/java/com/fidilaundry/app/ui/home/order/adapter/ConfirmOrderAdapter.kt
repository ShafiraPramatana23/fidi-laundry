package com.fidilaundry.app.ui.home.order.adapter

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.databinding.ItemConfirmOrderBinding
import com.fidilaundry.app.databinding.ItemSatuanBinding
import com.fidilaundry.app.model.response.ItemListResponse
import com.fidilaundry.app.model.response.OrderListResponse
import com.fidilaundry.app.ui.home.order.interfaces.IFItemClick
import com.fidilaundry.app.ui.home.order.interfaces.IFOrder
import com.fidilaundry.app.ui.home.order.interfaces.IFSatuan
import com.fidilaundry.app.ui.home.order.model.SelectedSatuanItem
import com.fidilaundry.app.util.DateFormater
import com.fidilaundry.app.util.DateTimeFormater
import com.fidilaundry.app.util.ServiceCtgHelper
import com.fidilaundry.app.util.dialog.DialogConfirmOrder
import com.fidilaundry.app.util.dialog.DialogDropdown
import com.fidilaundry.app.util.setSafeOnClickListener
import java.util.ArrayList

class ConfirmOrderAdapter(
    private val context: Context?,
    private val inf: IFOrder
) : RecyclerView.Adapter<ConfirmOrderAdapter.ViewHolder>() {

    private var appList: List<OrderListResponse.Result>
    lateinit var paperPrefs: PaperPrefs

    fun updateList(appList: List<OrderListResponse.Result>) {
        this.appList = appList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConfirmOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        paperPrefs = context?.let { PaperPrefs(it) }!!

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    inner class ViewHolder(private val binding: ItemConfirmOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val app = appList[position]
            binding.tvTitle.text = "Order - ${ServiceCtgHelper().getServiceName(app.serviceID.toString())}"
            binding.tvName.text = app.user?.name
            binding.tvDate.text = DateTimeFormater(app.createdAt!!)

            itemView.setSafeOnClickListener {
                inf.onItemClick(app)
            }
        }
    }

    init {
        appList = ArrayList()
    }

}