package com.fidilaundry.app.ui.home.orderimport android.Manifestimport android.content.Intentimport android.content.pm.PackageManagerimport android.location.Geocoderimport android.os.Bundleimport androidx.annotation.NonNullimport androidx.core.app.ActivityCompatimport androidx.lifecycle.Observerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.viewmodel.OrderViewModelimport com.fidilaundry.app.databinding.ActivityOrderUserBindingimport com.fidilaundry.app.model.request.OrderRequestimport com.fidilaundry.app.model.response.ItemListResponseimport com.fidilaundry.app.model.response.ProfileResponseimport com.fidilaundry.app.model.response.RequestOrderResponseimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.util.LoadingDialogimport com.fidilaundry.app.util.fdialog.ConfirmMessageimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.fdialog.FGCallbackimport com.fidilaundry.app.util.setSafeOnClickListenerimport com.google.android.gms.location.FusedLocationProviderClientimport com.google.android.gms.location.LocationServicesimport com.mapbox.mapboxsdk.Mapboximport com.mapbox.mapboxsdk.annotations.MarkerOptionsimport com.mapbox.mapboxsdk.camera.CameraPositionimport com.mapbox.mapboxsdk.camera.CameraUpdateFactoryimport com.mapbox.mapboxsdk.geometry.LatLngimport com.mapbox.mapboxsdk.maps.MapboxMapimport com.mapbox.mapboxsdk.maps.OnMapReadyCallbackimport com.mapbox.mapboxsdk.maps.Styleimport org.koin.androidx.viewmodel.ext.android.getViewModelclass UserOrderActivity : BaseActivity() {    lateinit var loadingDialog: LoadingDialog    private var idService: Int = 0    private var profileData: ProfileResponse.Results? = null    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient    private var longitude = 0.0    private var latitude = 0.0    private var address = ""    private val binding: ActivityOrderUserBinding by binding(R.layout.activity_order_user)    private val viewModel: OrderViewModel by lazy {        getViewModel(OrderViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))        binding.apply {            lifecycleOwner = this@UserOrderActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        idService = intent.getIntExtra("idService", 0)        profileData = paperPref.getDataProfile()        initViewModel()        viewModel.getItemsByService(idService)        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)        checkPermission()        binding.mapView.onCreate(savedInstanceState)//        initMaps()        binding.btnOrder.setSafeOnClickListener {            dialogConfirm()        }        binding.ivBack.setSafeOnClickListener {            finish()        }    }    private fun initMaps() {        // Setup the MapView        binding.mapView.getMapAsync(object : OnMapReadyCallback {            override fun onMapReady(@NonNull mapboxMap: MapboxMap) {                mapboxMap.setStyle(                    Style.MAPBOX_STREETS,                    Style.OnStyleLoaded { style ->                        val options = MarkerOptions()                        options.title("Current Position")                        options.position(LatLng(latitude, longitude))                        mapboxMap.addMarker(options)                        val position = CameraPosition.Builder()                            .target(LatLng(latitude, longitude))                            .zoom(10.0)                            .tilt(20.0)                            .build()                        mapboxMap.animateCamera(                            CameraUpdateFactory.newCameraPosition(position),                            1000                        )                    })            }        })    }    private fun checkPermission() {        if (ActivityCompat.checkSelfPermission(                this,                Manifest.permission.ACCESS_FINE_LOCATION            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(                this,                Manifest.permission.ACCESS_COARSE_LOCATION            ) != PackageManager.PERMISSION_GRANTED        ) {            ActivityCompat.requestPermissions(                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),                1            )            return        } else {            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->                if (location != null) {                    val geoCoder = Geocoder(this)                    val currentLocation = geoCoder.getFromLocation(                        location.latitude,                        location.longitude,                        1                    )                    println("omgggg lat: "+currentLocation?.first()?.latitude)                    println("omgggg long: "+currentLocation?.first()?.longitude)                    println("omgggg address: "+currentLocation?.first()?.getAddressLine(0))                    latitude = currentLocation?.first()?.latitude!!                    longitude = currentLocation?.first()?.longitude!!                    address = currentLocation?.first()?.getAddressLine(0)!!                    binding.tvAddress.text = address                    initMaps()                }            }        }    }    private fun initViewModel() {        viewModel.itemsListResponse.observe(this, Observer {            handleWhenItemListSuccess(it)        })        viewModel.orderResponse.observe(this, Observer {            handleWhenReqOrderSuccess(it)        })        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if (loadingDialog != null) {                    if (!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)        })    }    private fun handleWhenReqOrderSuccess(it: RequestOrderResponse?) {        val intent = Intent(baseContext, OrderMapsActivity::class.java)        startActivity(intent)        finish()    }    private fun handleWhenItemListSuccess(it: ItemListResponse?) {        println("whtaass : " + it)        var dt = it?.results?.filter { it.categoryID == "2" }        binding.tvDesc.text =            "Harga ${dt?.get(0)?.serviceTitle} Rp. ${dt?.get(0)?.price}/kg. Update harga akan dilakukan oleh petugas\n" +                    "setelah menimbang dan menyortir baju."        binding.tvType.text = dt?.get(0)?.serviceTitle        binding.tvName.text = profileData?.name    }    private fun dialogConfirm() {        ConfirmMessage(            this, "Apakah rincian pesanan Anda sudah benar?",            "", "", "Lanjutkan", "Batal",            object : FGCallback {                override fun onCallback() {                    var id = profileData?.id                    viewModel.requestOrder(                        OrderRequest(                            idService, latitude.toString(), longitude.toString(),                            address, "Antar Jemput", id!!, id!!                        )                    )                }            }        )    }    override fun onRequestPermissionsResult(        requestCode: Int,        permissions: Array<String?>,        grantResults: IntArray    ) {        super.onRequestPermissionsResult(requestCode, permissions, grantResults)        if (requestCode == 1) {            checkPermission()        }    }    override fun onStart() {        super.onStart()        binding.mapView.onStart()    }    override fun onStop() {        super.onStop()        binding.mapView.onStop()    }    override fun onLowMemory() {        super.onLowMemory()        binding.mapView.onLowMemory()    }    override fun onDestroy() {        super.onDestroy()        binding.mapView.onDestroy()    }}