package com.fidilaundry.app.ui.home.orderimport android.content.Intentimport android.os.Bundleimport android.view.Viewimport androidx.lifecycle.Observerimport androidx.recyclerview.widget.LinearLayoutManagerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.viewmodel.OrderViewModelimport com.fidilaundry.app.databinding.ActivityOrderDetailBindingimport com.fidilaundry.app.model.request.UpdateOrderStatusRequestimport com.fidilaundry.app.model.response.OrderDetailResponseimport com.fidilaundry.app.model.response.OrderListResponseimport com.fidilaundry.app.model.response.ProfileResponseimport com.fidilaundry.app.model.response.UpdateStatusResponseimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.ui.complaint.UserComplaintActivityimport com.fidilaundry.app.ui.home.order.adapter.SatuanDetailAdapterimport com.fidilaundry.app.ui.home.order.adapter.StatusOrderAdapterimport com.fidilaundry.app.ui.home.order.interfaces.IFItemClickimport com.fidilaundry.app.ui.home.order.interfaces.IFOrderimport com.fidilaundry.app.ui.home.order.model.StatusItemimport com.fidilaundry.app.util.*import com.fidilaundry.app.util.dialog.DialogConfirmOrderimport com.fidilaundry.app.util.dialog.DialogUpdateSteppingimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.fdialog.FGCallbackimport com.fidilaundry.app.util.fdialog.SuccessMessageimport org.koin.androidx.viewmodel.ext.android.getViewModelimport java.util.ArrayListclass OrderDetailActivity : BaseActivity(), IFItemClick, IFOrder {    lateinit var loadingDialog: LoadingDialog    private var adapter: StatusOrderAdapter? = null    private var satuanAdapter: SatuanDetailAdapter? = null    private var profileData: ProfileResponse.Results? = null    private var dtDetail: OrderDetailResponse.Result? = null    private var transId: String = ""    private var ctgList: MutableList<StatusItem> = ArrayList()    private val binding: ActivityOrderDetailBinding by binding(R.layout.activity_order_detail)    private val viewModel: OrderViewModel by lazy {        getViewModel(OrderViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        binding.apply {            lifecycleOwner = this@OrderDetailActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        transId = intent.getStringExtra("transId").toString()        initViewModel()        initCtg()        adapter = StatusOrderAdapter(this, this)        binding.rvStatus.layoutManager = ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)        binding.rvStatus.adapter = adapter        adapter?.updateList(ctgList, "")        satuanAdapter = SatuanDetailAdapter(this, this)        binding.rvSatuan.layoutManager = ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)        binding.rvSatuan.adapter = satuanAdapter        profileData = paperPref.getDataProfile()        if (profileData?.role == "customer" || profileData?.role == "member") {            layoutUser()        } else {            layoutAdmin()        }        binding.ivBack.setSafeOnClickListener {            finish()        }    }    private fun initCtg() {        ctgList.add(StatusItem(2, "Dijemput", "dijemput", false))        ctgList.add(StatusItem(3, "Pengecekan Baju", "cek item", false))        ctgList.add(StatusItem(4, "Pengerjaan", "pengerjaan", false))        ctgList.add(StatusItem(5, "Pengerjaan Selesai", "selesai-pengerjaan", false))        ctgList.add(StatusItem(6, "Diantar", "antar", false))        ctgList.add(StatusItem(7, "Selesai", "selesai", false))        ctgList.add(StatusItem(8, "Menunggu Pembayaran", "menunggu-pembayaran", false))        ctgList.add(StatusItem(9, "Pembayaran Selesai", "pembayaran-sukses", false))        ctgList.add(StatusItem(10, "Komplain", "komplain", false))        ctgList.add(StatusItem(11, "Komplain Terselesaikan", "solve", false))    }    private fun layoutUser() {        binding.btnComplaint.visibility = View.VISIBLE        binding.btnUpdateStatus.visibility = View.GONE        //tambah pengecekan komplen lewat dari 3 hari        binding.btnComplaint.setSafeOnClickListener {            val intent = Intent(this, UserComplaintActivity::class.java)            intent.putExtra("orderId", dtDetail?.id)            intent.putExtra("transId", dtDetail?.code)            startActivity(intent)        }    }    private fun layoutAdmin() {        binding.btnComplaint.visibility = View.GONE        binding.btnUpdateStatus.visibility = View.VISIBLE        binding.btnUpdateStatus.setSafeOnClickListener {            if (dtDetail?.status == "pengerjaan") {                val myRoundedBottomSheet = DialogUpdateStepping(dtDetail!!, this)                myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)            } else {                viewModel.updateOrderStatus(                    UpdateOrderStatusRequest(                        dtDetail?.code!!, StatusHelper.setStatus(dtDetail?.status!!), ""                    )                )            }        }    }    private fun initViewModel() {        viewModel.orderDetailResponse.observe(this, Observer {            handleWhenDetailSuccess(it)        })        viewModel.updateOrderStatusResponse.observe(this, Observer {            handleWhenUpdateStatusSuccess(it)        })        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if(loadingDialog != null){                    if(!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)        })    }    private fun handleWhenUpdateStatusSuccess(it: UpdateStatusResponse?) {        SuccessMessage(this, "Sukses", "Update status pesanan berhasil!", object : FGCallback {            override fun onCallback() {                finish()            }        })    }    private fun handleWhenDetailSuccess(it: OrderDetailResponse?) {        var dt = it?.results!!        dtDetail = dt        binding.tvOrderId.text = dt.id.toString()        binding.tvDate.text = DateTimeFormater(dt.createdAt!!)        binding.tvName.text = dt.orderFor?.name        binding.tvAddress.text = dt.addressDescription        binding.tvService.text = ServiceCtgHelper().getServiceName(dt.serviceID.toString())        binding.tvType.text = dt.transferMethod        binding.tvLayanan.text = dt.transferMethod        var subTotal = dt.total!! - dt.fee!!        binding.tvSubTotal.text = RupiahCurrency.Converter(subTotal.toDouble())        binding.tvValLayanan.text = RupiahCurrency.Converter(dt.fee!!.toDouble())        binding.tvTotal.text = RupiahCurrency.Converter(dt.total!!.toDouble())        var itemList: MutableList<OrderDetailResponse.OrderItem> = ArrayList()        for (i in dt.orderItems?.indices!!) {            var data = dt.orderItems!![i]            if (data.item?.categoryID == "2") {                binding.tvValKiloan.text = "${data.qty}kg x ${RupiahCurrency.Converter(data?.item?.price?.toDouble())}"                binding.tvTotalKiloan.text = RupiahCurrency.Converter(data.amount?.toDouble())            } else {                itemList.add(data)            }        }        satuanAdapter?.updateList(itemList)        for (i in ctgList.indices) {            ctgList[i].selected = ctgList[i].key == dt.status        }        adapter?.updateList(ctgList, dt.stepping.toString())        // USER        if (dt.status == "pembayaran-sukses") {            binding.btnComplaint.visibility = View.VISIBLE        } else {            binding.btnComplaint.visibility = View.GONE        }    }    override fun onResume() {        super.onResume()        viewModel.getOrderDetail(transId) //transId    }    override fun onItemClick() {//        val myRoundedBottomSheet = DialogConfirmOrder()//        myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)    }    override fun onItemSelected(value: String?, id: String) {    }    override fun onItemSelected(value: String?, id: String, type: Int) {    }    override fun onItemClick(data: OrderListResponse.Result) {    }    override fun onDialogDismiss() {        // after dialogupdatestepping        viewModel.getOrderDetail(transId) //transId    }}