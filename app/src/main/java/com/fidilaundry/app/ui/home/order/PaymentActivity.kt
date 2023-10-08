package com.fidilaundry.app.ui.home.orderimport android.os.Bundleimport androidx.lifecycle.Observerimport androidx.recyclerview.widget.LinearLayoutManagerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.basearch.viewmodel.HomeViewModelimport com.fidilaundry.app.basearch.viewmodel.OrderViewModelimport com.fidilaundry.app.databinding.ActivityOrderAdminBindingimport com.fidilaundry.app.databinding.ActivityOrderListBindingimport com.fidilaundry.app.databinding.ActivityOrderUserBindingimport com.fidilaundry.app.databinding.ActivityPaymentBindingimport com.fidilaundry.app.model.response.ItemListResponseimport com.fidilaundry.app.model.response.OrderListResponseimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.ui.history.adapter.HistoryAdapterimport com.fidilaundry.app.ui.home.master.adapter.ItemListAdapterimport com.fidilaundry.app.ui.home.master.adapter.PriceListAdapterimport com.fidilaundry.app.ui.home.master.model.DropdownItemimport com.fidilaundry.app.ui.home.order.adapter.ConfirmOrderAdapterimport com.fidilaundry.app.ui.home.order.adapter.SatuanAdapterimport com.fidilaundry.app.ui.home.order.interfaces.IFItemClickimport com.fidilaundry.app.ui.home.order.interfaces.IFOrderimport com.fidilaundry.app.ui.home.order.interfaces.IFSatuanimport com.fidilaundry.app.ui.home.order.model.SelectedSatuanItemimport com.fidilaundry.app.util.ListDivideritemDecorationimport com.fidilaundry.app.util.LoadingDialogimport com.fidilaundry.app.util.ScrollingLinearLayoutManagerimport com.fidilaundry.app.util.dialog.DialogAddDataimport com.fidilaundry.app.util.dialog.DialogConfirmOrderimport com.fidilaundry.app.util.dialog.DialogServiceimport com.fidilaundry.app.util.dialog.DialogShowQRimport com.fidilaundry.app.util.fdialog.ConfirmMessageimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.fdialog.FGCallbackimport com.fidilaundry.app.util.setSafeOnClickListenerimport kotlinx.android.synthetic.main.fragment_home.*import org.koin.androidx.viewmodel.ext.android.getViewModelimport java.util.ArrayListclass PaymentActivity : BaseActivity(), IFOrder {    lateinit var loadingDialog: LoadingDialog    private var adapter: HistoryAdapter? = null    private val binding: ActivityPaymentBinding by binding(R.layout.activity_payment)    private val viewModel: OrderViewModel by lazy {        getViewModel(OrderViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        binding.apply {            lifecycleOwner = this@PaymentActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        initViewModel()        binding.ivBack.setSafeOnClickListener {            finish()        }    }    private fun initViewModel() {//        viewModel.orderListResponse.observe(this, Observer {//            handleWhenListSuccess(it)//        })        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if(loadingDialog != null){                    if(!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)        })    }    override fun onResume() {        super.onResume()//        viewModel.getOrderList("","","", "")    }    override fun onItemClick(data: OrderListResponse.Result) {        val myRoundedBottomSheet = DialogConfirmOrder(data, this)        myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)    }    override fun onDialogDismiss() {        // refresh list//        viewModel.getOrderList("","","", "")    }}