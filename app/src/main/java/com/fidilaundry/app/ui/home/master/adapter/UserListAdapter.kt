package com.fidilaundry.app.ui.home.master.adapterimport android.content.Contextimport android.content.Intentimport android.view.LayoutInflaterimport android.view.ViewGroupimport androidx.core.content.res.ResourcesCompatimport androidx.recyclerview.widget.RecyclerViewimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.databinding.ItemUserBindingimport com.fidilaundry.app.model.response.CustomerListResponseimport com.fidilaundry.app.ui.home.master.AddUserActivityimport com.fidilaundry.app.util.setSafeOnClickListenerimport java.util.*class UserListAdapter(private val context: Context?) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {    private var appList: List<CustomerListResponse.Result>    lateinit var paperPrefs: PaperPrefs    fun updateList(appList: List<CustomerListResponse.Result>) {        this.appList = appList        notifyDataSetChanged()    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)        paperPrefs = PaperPrefs(this.context!!)        return ViewHolder(binding)    }    override fun onBindViewHolder(holder: ViewHolder, position: Int) {        holder.onBind(position)    }    override fun getItemCount(): Int {        return appList.size    }    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {        fun onBind(position: Int) {            var app = appList[position]            binding.tvName.text = app.name            itemView.setSafeOnClickListener {                val intent = Intent(context, AddUserActivity::class.java)                intent.putExtra("type", "2")                context!!.startActivity(intent)            }        }    }    init {        appList = ArrayList()    }}