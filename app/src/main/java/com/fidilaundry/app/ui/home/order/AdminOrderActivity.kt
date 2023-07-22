package com.fidilaundry.app.ui.home.orderimport android.os.Bundleimport androidx.lifecycle.Observerimport androidx.recyclerview.widget.LinearLayoutManagerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.basearch.viewmodel.HomeViewModelimport com.fidilaundry.app.basearch.viewmodel.OrderViewModelimport com.fidilaundry.app.databinding.ActivityOrderAdminBindingimport com.fidilaundry.app.databinding.ActivityOrderUserBindingimport com.fidilaundry.app.model.response.ItemListResponseimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.ui.home.master.adapter.ItemListAdapterimport com.fidilaundry.app.ui.home.master.adapter.PriceListAdapterimport com.fidilaundry.app.ui.home.order.adapter.SatuanAdapterimport com.fidilaundry.app.ui.home.order.interfaces.IFItemClickimport com.fidilaundry.app.ui.home.order.interfaces.IFSatuanimport com.fidilaundry.app.ui.home.order.model.SelectedSatuanItemimport com.fidilaundry.app.util.ListDivideritemDecorationimport com.fidilaundry.app.util.LoadingDialogimport com.fidilaundry.app.util.ScrollingLinearLayoutManagerimport com.fidilaundry.app.util.dialog.DialogAddDataimport com.fidilaundry.app.util.dialog.DialogServiceimport com.fidilaundry.app.util.fdialog.ConfirmMessageimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.fdialog.FGCallbackimport com.fidilaundry.app.util.setSafeOnClickListenerimport org.koin.androidx.viewmodel.ext.android.getViewModelimport java.util.ArrayListclass AdminOrderActivity : BaseActivity(), IFItemClick, IFSatuan {    lateinit var loadingDialog: LoadingDialog    private var typeForm = 0    private var adapter: SatuanAdapter? = null    private val binding: ActivityOrderAdminBinding by binding(R.layout.activity_order_admin)    private val viewModel: OrderViewModel by lazy {        getViewModel(OrderViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        binding.apply {            lifecycleOwner = this@AdminOrderActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        initViewModel()        var profileData = paperPref.getDataProfile()        if (profileData?.role == "customer" || profileData?.role == "member") {            binding.tvLayanan.text = "Antar - Jemput"        } else {            binding.tvLayanan.text = "Datang Langsung"        }        adapter = SatuanAdapter(this, this)        binding.rvSatuan.layoutManager =            ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)        binding.rvSatuan.adapter = adapter        viewModel.getItemsByService(1)        binding.btnOrder.setSafeOnClickListener {            dialogConfirm()        }        binding.ivBack.setSafeOnClickListener {            finish()        }        binding.etCategory.setSafeOnClickListener {            typeForm = 1            val myRoundedBottomSheet = DialogService(1, this)            myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)        }    }    private fun initViewModel() {        viewModel.itemsListResponse.observe(this, Observer {            handleWhenItemListSuccess(it)        })        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if(loadingDialog != null){                    if(!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)        })    }    private fun handleWhenItemListSuccess(it: ItemListResponse?) {        adapter?.updateList(it?.results!!)    }    private fun dialogConfirm() {        ConfirmMessage(            this, "Apakah rincian pesanan Anda sudah benar?",            "", "", "Lanjutkan", "Batal",            object : FGCallback {                override fun onCallback() {                }            }        )    }    override fun onItemClick() {    }    override fun onItemSelected(value: String?, id: String) {    }    override fun onItemSelected(value: String?, id: String, type: Int) {        when (typeForm) {            1 -> viewModel.categoryId.value = id            2 -> viewModel.serviceId.value = id        }    }    override fun onItemSelected(value: SelectedSatuanItem?) {    }}