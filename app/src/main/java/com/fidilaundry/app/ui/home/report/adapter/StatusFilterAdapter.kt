package com.fidilaundry.app.ui.home.report.adapterimport android.content.Contextimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.core.content.res.ResourcesCompatimport androidx.recyclerview.widget.RecyclerViewimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.databinding.ItemMasterBindingimport com.fidilaundry.app.databinding.ItemServiceBindingimport com.fidilaundry.app.databinding.ItemUserBindingimport com.fidilaundry.app.ui.home.master.model.DropdownItemimport com.fidilaundry.app.ui.home.order.interfaces.IFItemClickimport com.fidilaundry.app.ui.home.report.model.Statusimport java.util.*class StatusFilterAdapter(    private val context: Context?,    private val inf: IFItemClick) : RecyclerView.Adapter<StatusFilterAdapter.ViewHolder>() {    private var appList: List<Status>    lateinit var paperPrefs: PaperPrefs    private var selectedVal = ""    fun updateList(appList: List<Status>, selectedVal: String) {        this.appList = appList        this.selectedVal = selectedVal        notifyDataSetChanged()    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)        paperPrefs = PaperPrefs(this.context!!)        return ViewHolder(binding)    }    override fun onBindViewHolder(holder: ViewHolder, position: Int) {        holder.onBind(position)    }    override fun getItemCount(): Int {        return appList.size    }    inner class ViewHolder(private val binding: ItemServiceBinding) :        RecyclerView.ViewHolder(binding.root) {        fun onBind(position: Int) {            var app = appList[position]            binding.tvName.text = app.title            if (app.value == selectedVal){                binding.ivCheck.visibility = View.VISIBLE            } else {                binding.ivCheck.visibility = View.GONE            }            itemView.setOnClickListener{//                inf.onItemSelected(app.value, app.id.toString())                inf.onItemSelected(app.title, app.value, app.id)            }        }    }    init {        appList = ArrayList()    }}