package com.fidilaundry.app.ui.home.orderimport android.os.Bundleimport androidx.lifecycle.Observerimport androidx.recyclerview.widget.LinearLayoutManagerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.viewmodel.PaymentViewModelimport com.fidilaundry.app.databinding.ActivityPaymentListBindingimport com.fidilaundry.app.model.response.OrderListResponseimport com.fidilaundry.app.model.response.PaymentListResponseimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.ui.home.order.adapter.PaymentHistoryAdapterimport com.fidilaundry.app.ui.home.order.interfaces.IFOrderimport com.fidilaundry.app.util.LoadingDialogimport com.fidilaundry.app.util.ScrollingLinearLayoutManagerimport com.fidilaundry.app.util.dialog.DialogConfirmOrderimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.setSafeOnClickListenerimport org.koin.androidx.viewmodel.ext.android.getViewModelclass PaymentListActivity : BaseActivity(), IFOrder {    lateinit var loadingDialog: LoadingDialog    private var adapter: PaymentHistoryAdapter? = null    private val binding: ActivityPaymentListBinding by binding(R.layout.activity_payment_list)    private val viewModel: PaymentViewModel by lazy {        getViewModel(PaymentViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        binding.apply {            lifecycleOwner = this@PaymentListActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        initViewModel()        adapter = PaymentHistoryAdapter(this)        binding.rv.layoutManager =            ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)        binding.rv.adapter = adapter        binding.ivBack.setSafeOnClickListener {            finish()        }    }    private fun initViewModel() {        viewModel.paymentListResponse.observe(this, Observer {            handleWhenListSuccess(it)        })        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if(loadingDialog != null){                    if(!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)        })    }    private fun handleWhenListSuccess(it: PaymentListResponse?) {        it?.results?.let { it1 -> adapter?.updateList(it1.reversed()) }    }    override fun onResume() {        super.onResume()        viewModel.getPaymentList(0,"","", "")    }    override fun onItemClick(data: OrderListResponse.Result) {        val myRoundedBottomSheet = DialogConfirmOrder(data, this)        myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)    }    override fun onDialogDismiss() {        viewModel.getPaymentList(0,"","", "")    }}