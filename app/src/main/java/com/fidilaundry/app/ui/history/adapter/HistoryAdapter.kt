package com.fidilaundry.app.ui.history.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fidilaundry.app.ui.history.model.HistoryData
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.databinding.ItemHistoryBinding
import com.fidilaundry.app.model.response.OrderListResponse
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.ui.home.order.AdminOrderActivity
import com.fidilaundry.app.ui.home.order.OrderDetailActivity
import com.fidilaundry.app.util.DateTimeFormater
import com.fidilaundry.app.util.ServiceCtgHelper
import com.fidilaundry.app.util.setSafeOnClickListener
import kotlin.math.roundToInt

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
            binding.tvStatus.text = app.status?.capitalize()
//            binding.tvTotal.text = app.total.toString()

            itemView.setSafeOnClickListener {
                if (profileData?.role == "customer" || profileData?.role == "member") {
                    val intent = Intent(context, OrderDetailActivity::class.java)
                    intent.putExtra("transId", app.code)
                    context!!.startActivity(intent)
                } else {
                    if (app.status == "pending") {

                    } else if (app.status == "pengerjaan") {
                        val intent = Intent(context, OrderDetailActivity::class.java)
                        intent.putExtra("transId", app.code)
                        context!!.startActivity(intent)
                    } else {
                        val intent = Intent(context, AdminOrderActivity::class.java)
                        intent.putExtra("transId", app.code)
                        intent.putExtra("serviceId", app.serviceID)
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