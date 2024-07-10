package com.fidilaundry.app.ui.home.order.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.databinding.ItemPaymentBinding
import com.fidilaundry.app.model.response.PaymentListResponse
import com.fidilaundry.app.util.RupiahCurrency
import com.fidilaundry.app.util.ServiceCtgHelper
import java.util.ArrayList

class PaymentHistoryAdapter(
    private val context: Context?
) : RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder>() {

    private var appList: List<PaymentListResponse.Result>
    lateinit var paperPrefs: PaperPrefs

    fun updateList(appList: List<PaymentListResponse.Result>) {
        this.appList = appList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        paperPrefs = context?.let { PaperPrefs(it) }!!

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    inner class ViewHolder(private val binding: ItemPaymentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val app = appList[position]
            binding.tvName.text = app.order?.orderFor?.name
            var type = if (app.paymentType == 1) "Tunai" else "Non Tunai"
            binding.tvDesc.text = "${ServiceCtgHelper().getServiceName(app.order?.serviceID.toString())} . ${type}"
            binding.tvTotal.text = RupiahCurrency.Converter(app.payment?.toDouble())

            with(app.status) {
                when {
                    this?.contains("sukses")!! -> {
                        binding.tvStatusPay.text = "Berhasil"
                        binding.tvStatusPay.setTextColor(Color.GREEN)
                    }
                    this?.contains("menunggu")!! -> {
                        binding.tvStatusPay.text = "Pending"
                        binding.tvStatusPay.setTextColor(Color.GRAY)
                    }
                    else -> {
                        binding.tvStatusPay.text = "Gagal"
                        binding.tvStatusPay.setTextColor(Color.RED)
                    }
                }
            }
        }
    }

    init {
        appList = ArrayList()
    }

}