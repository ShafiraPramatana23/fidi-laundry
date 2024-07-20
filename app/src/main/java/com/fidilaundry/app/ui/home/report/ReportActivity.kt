package com.fidilaundry.app.ui.home.reportimport android.os.Bundleimport android.view.Viewimport androidx.lifecycle.Observerimport androidx.recyclerview.widget.LinearLayoutManagerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.basearch.viewmodel.HistoryViewModelimport com.fidilaundry.app.basearch.viewmodel.HomeViewModelimport com.fidilaundry.app.basearch.viewmodel.OrderViewModelimport com.fidilaundry.app.databinding.ActivityOrderAdminBindingimport com.fidilaundry.app.databinding.ActivityOrderListBindingimport com.fidilaundry.app.databinding.ActivityOrderUserBindingimport com.fidilaundry.app.databinding.ActivityReportBindingimport com.fidilaundry.app.model.response.ItemListResponseimport com.fidilaundry.app.model.response.OrderListResponseimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.ui.history.adapter.HistoryAdapterimport com.fidilaundry.app.ui.home.order.interfaces.IFOrderimport com.fidilaundry.app.ui.home.order.interfaces.IFSatuanimport com.fidilaundry.app.ui.home.order.model.SelectedSatuanItemimport com.fidilaundry.app.ui.scanner.interfaces.IFClickimport com.fidilaundry.app.util.*import com.fidilaundry.app.util.dialog.*import com.fidilaundry.app.util.fdialog.ConfirmMessageimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.fdialog.FGCallbackimport kotlinx.android.synthetic.main.fragment_home.*import org.koin.androidx.viewmodel.ext.android.getViewModelimport java.util.ArrayListclass ReportActivity : BaseActivity(), IFOrder, IFClick {    lateinit var loadingDialog: LoadingDialog    private var adapter: HistoryAdapter? = null    private val binding: ActivityReportBinding by binding(R.layout.activity_report)    private var countOnline: Int = 0    private var countOffline: Int = 0    private var qtyTrans: Int = 0    private var totalTrans: Double = 0.0    private var reportList: MutableList<OrderListResponse.Result> = ArrayList()    private val viewModel: HistoryViewModel by lazy {        getViewModel(HistoryViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        binding.apply {            lifecycleOwner = this@ReportActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        initViewModel()        adapter = HistoryAdapter(this)        binding.rv.layoutManager =            ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)        binding.rv.addItemDecoration(ListDivideritemDecoration(this))        binding.rv.adapter = adapter//        adapter?.updateList(ctgList)        binding.ivBack.setSafeOnClickListener {            finish()        }        binding.ivFilter.setSafeOnClickListener {            val myRoundedBottomSheet = DialogFilterReport(this)            myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)        }    }    private fun initViewModel() {        viewModel.reportResponse.observe(this, Observer {            handleWhenListSuccess(it)        })        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if(loadingDialog != null){                    if(!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)        })    }    private fun handleWhenListSuccess(it: OrderListResponse?) {        if (it?.results?.size!! > 0 ) {            reportList = it?.results!!.toMutableList()            binding.llEmpty.visibility = View.GONE        } else {            reportList.clear()            binding.llEmpty.visibility = View.VISIBLE        }        setData()        adapter?.updateList(reportList)    }    private fun setData() {        for (i in reportList.indices!!) {            var dt = reportList[i]            qtyTrans += 1            totalTrans += dt.total!!            if (dt.transferMethod == "Datang Langsung") {                countOffline += 1            }            if (dt.transferMethod == "Antar Jemput") {                countOnline += 1            }        }        binding.tvQty.text = "$qtyTrans"        binding.tvTotal.text = "${RupiahCurrency.Converter(totalTrans)}"        binding.tvOffline.text = "$countOffline"        binding.tvOnline.text = "$countOnline"    }    override fun onResume() {        super.onResume()        viewModel.getReport()    }    override fun onItemClick(data: OrderListResponse.Result) {        val myRoundedBottomSheet = DialogConfirmOrder(data, this)        myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)    }    override fun onDialogDismiss() {        // refresh list//        viewModel.getReport()    }    override fun onUserClick(id: Int) {    }    override fun onSubmitClick() {        println("filter statusnyee: "+viewModel.status.value)        println("filter typeFilter: "+viewModel.typeFilter.value)        println("filter year: "+viewModel.year.value)        println("filter startDate: "+viewModel.startDate.value)        println("filter endDate: "+viewModel.endDate.value)        qtyTrans = 0        totalTrans = 0.0        countOffline = 0        countOnline = 0        viewModel.getReport()    }}