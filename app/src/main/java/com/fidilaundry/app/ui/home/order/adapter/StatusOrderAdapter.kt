package com.fidilaundry.app.ui.home.order.adapter

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.databinding.ItemConfirmOrderBinding
import com.fidilaundry.app.databinding.ItemRincianStatusBinding
import com.fidilaundry.app.databinding.ItemSatuanBinding
import com.fidilaundry.app.model.response.ItemListResponse
import com.fidilaundry.app.ui.home.order.interfaces.IFItemClick
import com.fidilaundry.app.ui.home.order.interfaces.IFSatuan
import com.fidilaundry.app.ui.home.order.model.SelectedSatuanItem
import com.fidilaundry.app.ui.home.order.model.StatusItem
import com.fidilaundry.app.util.VectorDrawableUtils
import com.fidilaundry.app.util.dialog.DialogConfirmOrder
import com.fidilaundry.app.util.dialog.DialogDropdown
import com.fidilaundry.app.util.setSafeOnClickListener
import java.util.ArrayList

class StatusOrderAdapter(
    private val context: Context?,
    private val inf: IFItemClick
) : RecyclerView.Adapter<StatusOrderAdapter.ViewHolder>() {

    private var appList: List<StatusItem>
    lateinit var paperPrefs: PaperPrefs
    private var stepping: String = ""

    fun updateList(appList: List<StatusItem>, stepping: String) {
        this.appList = appList
        this.stepping = stepping
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRincianStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        paperPrefs = context?.let { PaperPrefs(it) }!!

        return ViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    inner class ViewHolder(private val binding: ItemRincianStatusBinding, viewType: Int) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.timeline?.initLine(viewType)
        }

        fun onBind(position: Int) {
            val app = appList[position]

            if (stepping != "" && app.title == "Pengerjaan") {
                binding.tvStatus.text = app.title + " (" + stepping + ")"
            } else {
                binding.tvStatus.text = app.title
            }

            when (position) {
                0 -> binding.timeline.setStartLineColor(ContextCompat.getColor(context!!, R.color.float_transparent), itemViewType)
                appList.size - 1 -> binding.timeline.setEndLineColor(ContextCompat.getColor(context!!, R.color.float_transparent), itemViewType)
                else -> binding.timeline.setStartLineColor(ContextCompat.getColor(context!!, R.color.colorGrey300), itemViewType)
            }

            binding.timeline.marker = VectorDrawableUtils.getDrawable(
                context!!,
                R.drawable.ic_marker_timeline,
                ContextCompat.getColor(context!!, R.color.colorGrey300)
            )

            if (app.selected) {
                setMarker(binding, R.drawable.ic_marker_timeline, R.color.colorPrimaryDark)
            } else {
                setMarker(binding, R.drawable.ic_marker_timeline, R.color.colorGrey300)
            }

        }
    }

    private fun setMarker(binding: ItemRincianStatusBinding, drawableResId: Int, colorFilter: Int) {
        binding.timeline.marker = VectorDrawableUtils.getDrawable(context!!, drawableResId, ContextCompat.getColor(context, colorFilter))
    }

    init {
        appList = ArrayList()
    }

}