package com.fidilaundry.app.ui.home.orderimport android.os.Bundleimport androidx.lifecycle.Observerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.basearch.viewmodel.HomeViewModelimport com.fidilaundry.app.basearch.viewmodel.OrderViewModelimport com.fidilaundry.app.databinding.ActivityOrderUserBindingimport com.fidilaundry.app.model.request.OrderRequestimport com.fidilaundry.app.model.response.BaseObjResponseimport com.fidilaundry.app.model.response.ItemListResponseimport com.fidilaundry.app.model.response.ProfileResponseimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.util.LoadingDialogimport com.fidilaundry.app.util.fdialog.ConfirmMessageimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.fdialog.FGCallbackimport com.fidilaundry.app.util.setSafeOnClickListenerimport org.koin.androidx.viewmodel.ext.android.getViewModelclass UserOrderActivity : BaseActivity() {    lateinit var loadingDialog: LoadingDialog    private var idService: Int = 0    private var profileData: ProfileResponse.Results? = null    private val binding: ActivityOrderUserBinding by binding(R.layout.activity_order_user)    private val viewModel: OrderViewModel by lazy {        getViewModel(OrderViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        binding.apply {            lifecycleOwner = this@UserOrderActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        idService = intent.getIntExtra("idService", 0)        profileData = paperPref.getDataProfile()        println("hihiiww: "+idService)        initViewModel()        viewModel.getItemsByService(idService)        binding.btnOrder.setSafeOnClickListener {            dialogConfirm()        }        binding.ivBack.setSafeOnClickListener {            finish()        }        binding.btnOrder.setSafeOnClickListener {            dialogConfirm()        }    }    private fun initViewModel() {        viewModel.itemsListResponse.observe(this, Observer {            handleWhenItemListSuccess(it)        })        viewModel.orderResponse.observe(this, Observer {            handleWhenReqOrderSuccess(it)        })        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if(loadingDialog != null){                    if(!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)//            when (showError) {////            }        })    }    private fun handleWhenReqOrderSuccess(it: BaseObjResponse?) {    }    private fun handleWhenItemListSuccess(it: ItemListResponse?) {        println("whtaass : "+it)        var dt = it?.results?.filter { it.categoryID == "2" }        binding.tvDesc.text = "Harga ${dt?.get(0)?.serviceTitle} Rp. ${dt?.get(0)?.price}/kg. Update harga akan dilakukan oleh petugas\n" +                "setelah menimbang dan menyortir baju."        binding.tvType.text = dt?.get(0)?.serviceTitle        binding.tvName.text = profileData?.name    }    private fun dialogConfirm() {        ConfirmMessage(            this, "Apakah rincian pesanan Anda sudah benar?",            "", "", "Lanjutkan", "Batal",            object : FGCallback {                override fun onCallback() {                    viewModel.requestOrder(OrderRequest(                        idService.toString(), "-7.5629624", "112.6794581", "mana aja", "Antar Jemput"                    ))                }            }        )    }}