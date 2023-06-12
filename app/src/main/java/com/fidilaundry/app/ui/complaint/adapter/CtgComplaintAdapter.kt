package com.fidilaundry.app.ui.complaint.adapterimport android.content.Contextimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.recyclerview.widget.RecyclerViewimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.databinding.ItemServiceBindingimport com.fidilaundry.app.ui.home.master.model.DropdownItemimport com.fidilaundry.app.ui.home.order.interfaces.IFItemClickimport java.util.*class CtgComplaintAdapter(    private val context: Context?,    private val inf: IFItemClick) : RecyclerView.Adapter<CtgComplaintAdapter.ViewHolder>() {    private var appList: List<DropdownItem>    lateinit var paperPrefs: PaperPrefs    private var selectedVal = ""    fun updateList(appList: List<DropdownItem>, selected: String) {        this.appList = appList        this.selectedVal = selected        notifyDataSetChanged()    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)        paperPrefs = PaperPrefs(this.context!!)        return ViewHolder(binding)    }    override fun onBindViewHolder(holder: ViewHolder, position: Int) {        holder.onBind(position)    }    override fun getItemCount(): Int {        return appList.size    }    inner class ViewHolder(private val binding: ItemServiceBinding) :        RecyclerView.ViewHolder(binding.root) {        fun onBind(position: Int) {            var app = appList[position]            binding.tvName.text = app.title            if (app.title == selectedVal){                binding.ivCheck.visibility = View.VISIBLE            } else {                binding.ivCheck.visibility = View.GONE            }            itemView.setOnClickListener{                binding.ivCheck.visibility = View.VISIBLE                inf.onItemSelected(app.title, app.id.toString())                notifyDataSetChanged()            }        }    }    init {        appList = ArrayList()    }}