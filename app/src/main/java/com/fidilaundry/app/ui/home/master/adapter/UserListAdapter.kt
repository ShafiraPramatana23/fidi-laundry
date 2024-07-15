package com.fidilaundry.app.ui.home.master.adapterimport android.content.Contextimport android.content.Intentimport android.view.LayoutInflaterimport android.view.ViewGroupimport androidx.recyclerview.widget.RecyclerViewimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.databinding.ItemUserBindingimport com.fidilaundry.app.model.response.CustomerListResponseimport com.fidilaundry.app.ui.home.master.AddUserActivityimport com.fidilaundry.app.ui.scanner.interfaces.IFClickimport com.fidilaundry.app.util.setSafeOnClickListenerimport java.util.*class UserListAdapter(    private val context: Context?,    private val type: Int,    private val inf: IFClick) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {    private var appList: List<CustomerListResponse.Result>    lateinit var paperPrefs: PaperPrefs    fun updateList(appList: List<CustomerListResponse.Result>) {        this.appList = appList        notifyDataSetChanged()    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)        paperPrefs = PaperPrefs(this.context!!)        return ViewHolder(binding)    }    override fun onBindViewHolder(holder: ViewHolder, position: Int) {        holder.onBind(position)    }    override fun getItemCount(): Int {        return appList.size    }    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {        fun onBind(position: Int) {            var app = appList[position]            binding.tvName.text = app.name            itemView.setSafeOnClickListener {                if (type == 1) {                    inf.onUserClick(app.id!!)                } else {                    val intent = Intent(context, AddUserActivity::class.java)                    intent.putExtra("type", "2")                    intent.putExtra("userId", app.id)                    intent.putExtra("data", app)                    context!!.startActivity(intent)                }            }        }    }    init {        appList = ArrayList()    }}