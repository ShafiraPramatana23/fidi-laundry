package com.fidilaundry.app.ui.authimport android.content.Intentimport android.os.Bundleimport androidx.lifecycle.Observerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.localpref.PaperPrefsimport com.fidilaundry.app.basearch.viewmodel.LoginViewModelimport com.fidilaundry.app.databinding.ActivityLoginBindingimport com.fidilaundry.app.model.response.LoginResponseimport com.fidilaundry.app.model.response.ProfileResponseimport com.fidilaundry.app.ui.MainActivityimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.ui.home.order.OrderMapsActivityimport com.fidilaundry.app.util.LoadingDialogimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.setSafeOnClickListenerimport com.onesignal.OneSignalimport org.json.JSONExceptionimport org.koin.androidx.viewmodel.ext.android.getViewModelclass LoginActivity : BaseActivity() {    lateinit var paperPrefs: PaperPrefs    lateinit var loadingDialog: LoadingDialog    private val binding: ActivityLoginBinding by binding(R.layout.activity_login)    private val viewModel: LoginViewModel by lazy {        getViewModel(LoginViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        binding.apply {            lifecycleOwner = this@LoginActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        paperPrefs = PaperPrefs(this@LoginActivity)        initViewModel()        println("onesignal playerid: "+ getOneSignalUserID())//        binding.etUsername.setText("fira@gmail.com")//        binding.etPassword.setText("#00itpoke")        viewModel.loginUsername.value = "laundry@gmail.com"        viewModel.loginPassword.value = "#00itpoke"        binding.btnRegister.setSafeOnClickListener {//            intent = Intent(this@LoginActivity, OrderMapsActivity::class.java)//            startActivity(intent)            println("onesignal playerid: "+ getOneSignalUserID())        }        binding.btnLogin.setSafeOnClickListener {            viewModel.loginInit(getOneSignalUserID())//            intent = Intent(this@LoginActivity, MainActivity::class.java)//            startActivity(intent)        }    }    private fun initViewModel() {        viewModel.loginResponse.observe(this, Observer {            handleWhenLoginSuccess(it)        })        viewModel.profileResponse.observe(this, Observer {            handleWhenProfileSuccess(it)        })        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if(loadingDialog != null){                    if(!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)//            when (showError) {////            }        })    }    private fun handleWhenProfileSuccess(it: ProfileResponse?) {        paperPrefs.setDataProfile(it?.results!!)        intent = Intent(this@LoginActivity, MainActivity::class.java)        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK        startActivity(intent)        finish()    }    private fun handleWhenLoginSuccess(it: LoginResponse?) {        paperPrefs.setToken(it?.results?.token.toString())        viewModel.getProfile()    }    private fun getOneSignalUserID(): String {        var userId = ""        try {            userId = if (OneSignal.getDeviceState()!!.userId == null)                ""            else{                if(OneSignal.getDeviceState()!!.userId  == null)                    ""                else                    OneSignal.getDeviceState()!!.userId            }        } catch (e: JSONException) {            userId = ""        }        return userId    }}