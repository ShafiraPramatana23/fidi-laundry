package com.fidilaundry.app.ui.home.orderimport android.os.Bundleimport androidx.lifecycle.Observerimport androidx.recyclerview.widget.LinearLayoutManagerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.basearch.viewmodel.HomeViewModelimport com.fidilaundry.app.basearch.viewmodel.OrderViewModelimport com.fidilaundry.app.databinding.ActivityOrderAdminBindingimport com.fidilaundry.app.databinding.ActivityOrderUserBindingimport com.fidilaundry.app.model.request.ItemServiceimport com.fidilaundry.app.model.request.UpdateOrderRequestimport com.fidilaundry.app.model.response.BaseResponseimport com.fidilaundry.app.model.response.ItemListResponseimport com.fidilaundry.app.model.response.OrderDetailResponseimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.ui.home.master.adapter.ItemListAdapterimport com.fidilaundry.app.ui.home.master.adapter.PriceListAdapterimport com.fidilaundry.app.ui.home.order.adapter.SatuanAdapterimport com.fidilaundry.app.ui.home.order.interfaces.IFItemClickimport com.fidilaundry.app.ui.home.order.interfaces.IFSatuanimport com.fidilaundry.app.ui.home.order.model.SelectedSatuanItemimport com.fidilaundry.app.util.*import com.fidilaundry.app.util.dialog.DialogAddDataimport com.fidilaundry.app.util.dialog.DialogServiceimport com.fidilaundry.app.util.fdialog.ConfirmMessageimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.fdialog.FGCallbackimport com.fidilaundry.app.util.fdialog.SuccessMessageimport kotlinx.android.synthetic.main.fancygifdialog.*import org.koin.androidx.viewmodel.ext.android.getViewModelimport java.util.ArrayListclass AdminOrderActivity : BaseActivity(), IFItemClick, IFSatuan {    lateinit var loadingDialog: LoadingDialog    private var typeForm = 0    private var transId = ""    private var serviceId = 0    private var idKiloan = 0    private var dtKiloan: ItemListResponse.Result? = null    private var total = 0    private var fee = 0    private var selectedItem: MutableList<ItemService> = ArrayList()    private var dtDetail: OrderDetailResponse.Result? = null    private var adapter: SatuanAdapter? = null    private val binding: ActivityOrderAdminBinding by binding(R.layout.activity_order_admin)    private val viewModel: OrderViewModel by lazy {        getViewModel(OrderViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        binding.apply {            lifecycleOwner = this@AdminOrderActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        initViewModel()        transId = intent.getStringExtra("transId").toString()        serviceId = intent.getIntExtra("serviceId", 0)        var profileData = paperPref.getDataProfile()        if (profileData?.role == "customer" || profileData?.role == "member") {            binding.tvLayanan.text = "Antar - Jemput"        } else {            binding.tvLayanan.text = "Datang Langsung"        }        adapter = SatuanAdapter(this, this)        binding.rvSatuan.layoutManager =            ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)        binding.rvSatuan.adapter = adapter        viewModel.getItemsByService(serviceId)        viewModel.getOrderDetail(transId)        binding.btnOrder.setSafeOnClickListener {            dialogConfirm()        }        binding.ivBack.setSafeOnClickListener {            finish()        }        binding.etCategory.setSafeOnClickListener {            typeForm = 1            val myRoundedBottomSheet = DialogService(1, this)            myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)        }    }    private fun initViewModel() {        viewModel.itemsListResponse.observe(this, Observer {            handleWhenItemListSuccess(it)        })        viewModel.orderDetailResponse.observe(this, Observer {            handleWhenDetailSuccess(it)        })        viewModel.kiloan.observe(this, Observer {            dataChange()        })                viewModel.updateOrderResponse.observe(this, Observer {            handleWhenUpdateSuccess(it)        })        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if(loadingDialog != null){                    if(!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)        })    }    private fun handleWhenUpdateSuccess(it: BaseResponse?) {        SuccessMessage(this, "Sukses", "Update pesanan berhasil!", object : FGCallback {            override fun onCallback() {                finish()            }        })    }    private fun dataChange() {        total = 0        var idx = selectedItem.indexOfFirst { it.item_id == idKiloan }        var totAmount = if (viewModel.kiloan.value == "") {            dtKiloan?.price!! * 0        } else {            dtKiloan?.price!! * viewModel.kiloan.value.toInt()        }        val updateQty = selectedItem[idx].apply {            qty = if (viewModel.kiloan.value == "") 0 else viewModel.kiloan.value.toInt()            amount = totAmount        }        selectedItem[idx] = updateQty        for (i in selectedItem.indices) {            total += selectedItem[i].amount        }        binding.tvItemTotal.text = "${selectedItem.size} item"        binding.tvSubTotal.text = RupiahCurrency.Converter(total.toDouble())        if (dtDetail?.transferMethod == "Antar Jemput") {            fee = 5000            total += fee            binding.tvPriceTotal.text = RupiahCurrency.Converter(5000.0)            binding.tvPriceTotal.text = RupiahCurrency.Converter(total.toDouble())        } else {            binding.tvPriceTotal.text = RupiahCurrency.Converter(0.0)            binding.tvPriceTotal.text = RupiahCurrency.Converter(total.toDouble())        }    }    private fun handleWhenDetailSuccess(it: OrderDetailResponse?) {        dtDetail = it?.results        binding.tvName.text = dtDetail?.user?.name        binding.tvDate.text = DateTimeFormater(dtDetail?.createdAt!!)        binding.tvAddress.text = dtDetail?.addressDescription        binding.tvService.text = ServiceCtgHelper().getServiceName(dtDetail?.serviceID.toString())        binding.tvLayanan.text = dtDetail?.transferMethod    }    private fun handleWhenItemListSuccess(it: ItemListResponse?) {        var dt = it?.results?.filter { it.categoryID == "2" }        idKiloan = dt?.get(0)?.id!!        dtKiloan = dt?.get(0)        selectedItem.add(ItemService(idKiloan, 0, 0))        adapter?.updateList(it?.results!!)    }    private fun dialogConfirm() {        ConfirmMessage(            this, "Apakah rincian pesanan Anda sudah benar?",            "", "", "Lanjutkan", "Batal",            object : FGCallback {                override fun onCallback() {                    updateAction()                }            }        )    }    private fun updateAction() {        dataChange()        viewModel.updateOrder(            UpdateOrderRequest(                transId, StatusHelper.setStatus("cek item"), dtDetail?.latitude!!,                dtDetail?.longitude!!, "", selectedItem, fee, total            )        )    }    override fun onItemClick() {    }    override fun onItemSelected(value: String?, id: String) {    }    override fun onItemSelected(value: String?, id: String, type: Int) {        when (typeForm) {            1 -> viewModel.categoryId.value = id            2 -> viewModel.serviceId.value = id        }    }    override fun onItemSelected(value: SelectedSatuanItem?) {        var idx = selectedItem.indexOfFirst { it.item_id == value?.id }        var totalAmount = value?.price!! * value?.qty!!        if (idx >= 0) {            val updateQty = selectedItem[idx].apply {                qty = value?.qty!!                amount = totalAmount            }            selectedItem[idx] = updateQty        } else {            selectedItem.add(ItemService(value?.id!!, totalAmount, value.qty))        }        dataChange()    }}