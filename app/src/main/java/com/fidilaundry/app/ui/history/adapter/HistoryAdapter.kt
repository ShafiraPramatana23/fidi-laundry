package com.fidilaundry.app.ui.history.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.databinding.ItemHistoryBinding
import com.fidilaundry.app.model.response.OrderListResponse
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.ui.home.order.AdminOrderActivity
import com.fidilaundry.app.ui.home.order.AdminOrderMapsActivity
import com.fidilaundry.app.ui.home.order.OrderDetailActivity
import com.fidilaundry.app.ui.home.order.OrderMapsActivity
import com.fidilaundry.app.util.*

class HistoryAdapter(private val context: Context?) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var appList: List<OrderListResponse.Result>

    lateinit var paperPref: PaperPrefs
    private var profileData: ProfileResponse.Results? = null

    fun updateList(appList: List<OrderListResponse.Result>) {
        this.appList = appList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        paperPref = PaperPrefs(context!!)
        profileData = paperPref.getDataProfile()

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
            binding.tvType.text = ServiceCtgHelper().getServiceName(app.serviceID.toString())
            binding.tvDate.text = DateTimeFormater(app.createdAt!!)
            binding.tvStatus.text = StatusHelper.setStatusName(app.status.toString())
            binding.tvTotal.text = RupiahCurrency.Converter(app.total?.toDouble())
            binding.iv.setImageResource(ServiceCtgHelper().getImgService(app.serviceID!!))

            itemView.setSafeOnClickListener {
                if (profileData?.role == "customer" || profileData?.role == "member") {
                    if (app.status == "dijemput") {
                        val intent = Intent(context, OrderMapsActivity::class.java)
                        intent.putExtra("orderId", app.id)
                        intent.putExtra("orderCode", app.code)
                        context!!.startActivity(intent)
                    } else {
                        val intent = Intent(context, OrderDetailActivity::class.java)
                        intent.putExtra("transId", app.code)
                        intent.putExtra("orderId", app.id)
                        context!!.startActivity(intent)
                    }
                } else {
                    if (app.status == "pending") {

                    } else if (app.status == "cek item") {
                        val intent = Intent(context, AdminOrderActivity::class.java)
                        intent.putExtra("transId", app.code)
                        intent.putExtra("serviceId", app.serviceID)
                        context!!.startActivity(intent)
                    } else if (app.status == "dijemput") {
                        val intent = Intent(context, AdminOrderMapsActivity::class.java)
                        intent.putExtra("data", app)
                        context!!.startActivity(intent)
                    } else {
                        val intent = Intent(context, OrderDetailActivity::class.java)
                        intent.putExtra("transId", app.code)
                        intent.putExtra("orderId", app.id)
                        context!!.startActivity(intent)
                    }
                }
            }
        }
    }

    init {
        appList = ArrayList()
    }
}