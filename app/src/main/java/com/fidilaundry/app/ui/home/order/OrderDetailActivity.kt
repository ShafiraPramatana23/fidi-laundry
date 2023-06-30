package com.fidilaundry.app.ui.home.orderimport android.os.Bundleimport androidx.lifecycle.Observerimport androidx.recyclerview.widget.LinearLayoutManagerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.basearch.viewmodel.HomeViewModelimport com.fidilaundry.app.basearch.viewmodel.OrderViewModelimport com.fidilaundry.app.databinding.ActivityOrderAdminBindingimport com.fidilaundry.app.databinding.ActivityOrderListBindingimport com.fidilaundry.app.databinding.ActivityOrderUserBindingimport com.fidilaundry.app.model.response.ItemListResponseimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.ui.home.master.adapter.ItemListAdapterimport com.fidilaundry.app.ui.home.master.adapter.PriceListAdapterimport com.fidilaundry.app.ui.home.master.model.DropdownItemimport com.fidilaundry.app.ui.home.order.adapter.ConfirmOrderAdapterimport com.fidilaundry.app.ui.home.order.adapter.SatuanAdapterimport com.fidilaundry.app.ui.home.order.interfaces.IFItemClickimport com.fidilaundry.app.ui.home.order.interfaces.IFSatuanimport com.fidilaundry.app.ui.home.order.model.SelectedSatuanItemimport com.fidilaundry.app.util.ListDivideritemDecorationimport com.fidilaundry.app.util.LoadingDialogimport com.fidilaundry.app.util.ScrollingLinearLayoutManagerimport com.fidilaundry.app.util.dialog.DialogAddDataimport com.fidilaundry.app.util.dialog.DialogConfirmOrderimport com.fidilaundry.app.util.dialog.DialogServiceimport com.fidilaundry.app.util.dialog.DialogShowQRimport com.fidilaundry.app.util.fdialog.ConfirmMessageimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.fdialog.FGCallbackimport com.fidilaundry.app.util.setSafeOnClickListenerimport org.koin.androidx.viewmodel.ext.android.getViewModelimport java.util.ArrayListclass OrderDetailActivity : BaseActivity(), IFItemClick {    lateinit var loadingDialog: LoadingDialog    private var adapter: ConfirmOrderAdapter? = null    private val binding: ActivityOrderListBinding by binding(R.layout.activity_order_list)    private val viewModel: OrderViewModel by lazy {        getViewModel(OrderViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        binding.apply {            lifecycleOwner = this@OrderDetailActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        initViewModel()        adapter = ConfirmOrderAdapter(this, this)        binding.rv.layoutManager =            ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)        binding.rv.adapter = adapter        adapter?.updateList(ctgList)        binding.ivBack.setSafeOnClickListener {            finish()        }    }    private val ctgList: List<String>        get() {            val appList: MutableList<String> = ArrayList()            appList.add("Pakaian Hilang")            appList.add("Pakaian Hilang")            appList.add("Pakaian Hilang")            return appList        }    private fun initViewModel() {        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if(loadingDialog != null){                    if(!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)        })    }    override fun onItemClick() {        val myRoundedBottomSheet = DialogConfirmOrder()        myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)    }    override fun onItemSelected(value: String?, id: String) {    }    override fun onItemSelected(value: String?, id: String, type: Int) {    }}