package com.fidilaundry.app.ui.complaint.adapterimport android.content.Contextimport android.content.Intentimport android.view.LayoutInflaterimport android.view.ViewGroupimport androidx.core.content.res.ResourcesCompatimport androidx.recyclerview.widget.RecyclerViewimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.databinding.ItemComplaintListBindingimport com.fidilaundry.app.databinding.ItemPriceBindingimport com.fidilaundry.app.databinding.ItemUserBindingimport com.fidilaundry.app.model.response.ComplaintListResponseimport com.fidilaundry.app.ui.complaint.AdminComplaintActivityimport com.fidilaundry.app.ui.home.order.AdminOrderActivityimport com.fidilaundry.app.ui.home.order.AdminOrderMapsActivityimport com.fidilaundry.app.util.ServiceCtgHelperimport com.fidilaundry.app.util.setSafeOnClickListenerimport java.util.*class ComplaintListAdapter(private val context: Context?) : RecyclerView.Adapter<ComplaintListAdapter.ViewHolder>() {    private var appList: List<ComplaintListResponse.Result>    lateinit var paperPrefs: PaperPrefs    fun updateList(appList: List<ComplaintListResponse.Result>) {        this.appList = appList        notifyDataSetChanged()    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {        val binding = ItemComplaintListBinding.inflate(LayoutInflater.from(parent.context), parent, false)        paperPrefs = PaperPrefs(this.context!!)        return ViewHolder(binding)    }    override fun onBindViewHolder(holder: ViewHolder, position: Int) {        holder.onBind(position)    }    override fun getItemCount(): Int {        return appList.size    }    inner class ViewHolder(private val binding: ItemComplaintListBinding) : RecyclerView.ViewHolder(binding.root) {        fun onBind(position: Int) {            var app = appList[position]            binding.tvTitle.text = app.category            binding.tvDesc.text = app.description            binding.tvStatus.text = "-"            binding.ivIcon.setImageResource(ServiceCtgHelper().getImgService(app.order?.serviceID!!))            itemView.setSafeOnClickListener {                val intent = Intent(context, AdminComplaintActivity::class.java)                intent.putExtra("data", app)                context!!.startActivity(intent)            }        }    }    init {        appList = ArrayList()    }}