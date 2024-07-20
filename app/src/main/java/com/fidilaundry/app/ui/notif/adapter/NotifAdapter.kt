package com.fidilaundry.app.ui.notif.adapter

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
import com.fidilaundry.app.databinding.ItemNotifBinding
import com.fidilaundry.app.databinding.ItemProfileMenuBinding
import com.fidilaundry.app.model.response.NotifResponse
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.ui.MainActivity
import com.fidilaundry.app.ui.complaint.ComplaintActivity
import com.fidilaundry.app.ui.complaint.UserComplaintActivity
import com.fidilaundry.app.ui.home.order.AdminOrderMapsActivity
import com.fidilaundry.app.ui.home.order.OrderDetailActivity
import com.fidilaundry.app.ui.home.order.OrderMapsActivity
import com.fidilaundry.app.ui.home.order.interfaces.IFOrder
import com.fidilaundry.app.ui.profile.ChangePassActivity
import com.fidilaundry.app.ui.profile.ChangeProfileActivity
import com.fidilaundry.app.ui.profile.interfaces.IProfile
import com.fidilaundry.app.ui.profile.model.ProfileMenu
import com.fidilaundry.app.util.DateTimeFormater
import com.fidilaundry.app.util.ServiceCtgHelper
import com.fidilaundry.app.util.dialog.DialogConfirmOrder
import com.fidilaundry.app.util.setSafeOnClickListener
import kotlin.math.roundToInt

class NotifAdapter(private val context: Context?, private val inf: IFOrder) :
    RecyclerView.Adapter<NotifAdapter.ViewHolder>() {

    lateinit var paperPref: PaperPrefs
    private var profileData: ProfileResponse.Results? = null
    private var appList: List<NotifResponse.Results>

    fun updateList(appList: List<NotifResponse.Results>) {
        this.appList = appList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotifBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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

    inner class ViewHolder(private val binding: ItemNotifBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val app = appList[position]
            binding.tvTitle.text = ServiceCtgHelper().getServiceName(app.order?.serviceID.toString())
            binding.iv.setImageResource(ServiceCtgHelper().getImgService(app.order?.serviceID!!))
            binding.tvDate.text = DateTimeFormater(app.createdAt!!)
            binding.tvDesc.text = app.message

            itemView.setSafeOnClickListener {
                if (profileData?.role == "customer" || profileData?.role == "member") {
                    if (app.order?.status == "dijemput") {
                        val intent = Intent(context, OrderMapsActivity::class.java)
                        intent.putExtra("orderId", app.id)
                        context!!.startActivity(intent)
                    } else {
                        var intent = Intent(context, OrderDetailActivity::class.java)
                        intent.putExtra("transId", app.order?.code)
                        intent.putExtra("orderId", app.order?.id)
                        context!!.startActivity(intent)
                    }
                } else {
                    if (app.order?.status == "pending") {
                        inf.onItemClick(app.order!!)
                    } else if (app.order?.status == "dijemput") {
                        val intent = Intent(context, AdminOrderMapsActivity::class.java)
                        intent.putExtra("data", app.order)
                        context!!.startActivity(intent)
                    } else {
                        var intent = Intent(context, OrderDetailActivity::class.java)
                        intent.putExtra("transId", app.order?.code)
                        intent.putExtra("orderId", app.order?.id)
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