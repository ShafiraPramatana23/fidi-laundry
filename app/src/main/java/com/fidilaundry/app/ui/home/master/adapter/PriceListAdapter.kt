package com.fidilaundry.app.ui.home.master.adapterimport android.content.Contextimport android.content.Intentimport android.view.LayoutInflaterimport android.view.ViewGroupimport androidx.core.content.res.ResourcesCompatimport androidx.recyclerview.widget.RecyclerViewimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.databinding.ItemPriceBindingimport com.fidilaundry.app.databinding.ItemUserBindingimport com.fidilaundry.app.model.response.ItemListResponseimport com.fidilaundry.app.ui.home.master.AddPriceActivityimport com.fidilaundry.app.util.setSafeOnClickListenerimport java.util.*class PriceListAdapter(private val context: Context?) : RecyclerView.Adapter<PriceListAdapter.ViewHolder>() {    private var appList: List<ItemListResponse.Result>    lateinit var paperPrefs: PaperPrefs    fun updateList(appList: List<ItemListResponse.Result>) {        this.appList = appList        notifyDataSetChanged()    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {        val binding = ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)        paperPrefs = PaperPrefs(this.context!!)        return ViewHolder(binding)    }    override fun onBindViewHolder(holder: ViewHolder, position: Int) {        holder.onBind(position)    }    override fun getItemCount(): Int {        return appList.size    }    inner class ViewHolder(private val binding: ItemPriceBinding) : RecyclerView.ViewHolder(binding.root) {        fun onBind(position: Int) {            var app = appList[position]            binding.tvType.text = app.title            binding.tvService.text = app.serviceTitle            binding.tvCtg.text = app.categoryTitle            binding.tvTotal.text = app.price.toString()            itemView.setSafeOnClickListener {                val intent = Intent(context, AddPriceActivity::class.java)                intent.putExtra("type", "2")                intent.putExtra("data", app)                context!!.startActivity(intent)            }        }    }    init {        appList = ArrayList()    }}