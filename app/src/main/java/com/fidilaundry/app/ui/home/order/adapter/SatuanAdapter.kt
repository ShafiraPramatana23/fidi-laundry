package com.fidilaundry.app.ui.home.order.adapter

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.databinding.ItemSatuanBinding
import com.fidilaundry.app.model.response.ItemListResponse
import com.fidilaundry.app.ui.home.order.interfaces.IFSatuan
import com.fidilaundry.app.ui.home.order.model.SelectedSatuanItem
import com.fidilaundry.app.util.RupiahCurrency
import java.util.ArrayList

class SatuanAdapter(
    private val context: Context?,
    private val inf: IFSatuan
) : RecyclerView.Adapter<SatuanAdapter.ViewHolder>() {

    private var appList: List<ItemListResponse.Result>
    lateinit var paperPrefs: PaperPrefs

    fun updateList(appList: List<ItemListResponse.Result>) {
        this.appList = appList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSatuanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        paperPrefs = context?.let { PaperPrefs(it) }!!

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    inner class ViewHolder(private val binding: ItemSatuanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val app = appList[position]

            if (app.categoryID == "2") {
                itemView.visibility = View.GONE
                itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
            } else {
                itemView.visibility = View.VISIBLE
                itemView.layoutParams = RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            binding.etQty.inputType = InputType.TYPE_NULL
            binding.tvName.text = app.title
            binding.tvPrice.text = RupiahCurrency.Converter(app.price?.toDouble())

            var count = 0
            binding.layInc.setOnClickListener {
                count += 1
                binding.etQty.setText(""+count)
                setData(app, count)
            }

            binding.layDec.setOnClickListener {
                count -= 1
                binding.etQty.setText(""+count)
                setData(app, count)
            }
        }
    }

    private fun setData(app: ItemListResponse.Result, count: Int) {
        inf.onItemSelected(
            SelectedSatuanItem(
                app.id!!, app.title!!, app.price!!, count
            )
        )
    }

    init {
        appList = ArrayList()
    }

}