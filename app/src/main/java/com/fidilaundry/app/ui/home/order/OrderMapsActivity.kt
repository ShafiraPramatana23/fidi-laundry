package com.fidilaundry.app.ui.home.orderimport android.Manifestimport android.content.Intentimport android.content.pm.PackageManagerimport android.graphics.Colorimport android.location.Geocoderimport android.location.Locationimport android.os.Bundleimport android.widget.Toastimport androidx.annotation.NonNullimport androidx.core.app.ActivityCompatimport androidx.lifecycle.Observerimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.viewmodel.TrackingViewModelimport com.fidilaundry.app.databinding.ActivityOrderMapsBindingimport com.fidilaundry.app.model.response.OrderDetailResponseimport com.fidilaundry.app.model.response.TrackingListResponseimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.util.LoadingDialogimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.setSafeOnClickListenerimport com.google.android.gms.location.FusedLocationProviderClientimport com.google.android.gms.location.LocationServicesimport com.mapbox.api.directions.v5.DirectionsCriteriaimport com.mapbox.api.directions.v5.MapboxDirectionsimport com.mapbox.api.directions.v5.models.DirectionsResponseimport com.mapbox.api.directions.v5.models.DirectionsRouteimport com.mapbox.core.constants.Constants.PRECISION_6import com.mapbox.geojson.Featureimport com.mapbox.geojson.FeatureCollectionimport com.mapbox.geojson.LineStringimport com.mapbox.geojson.Pointimport com.mapbox.mapboxsdk.Mapboximport com.mapbox.mapboxsdk.camera.CameraPositionimport com.mapbox.mapboxsdk.camera.CameraUpdateFactoryimport com.mapbox.mapboxsdk.geometry.LatLngimport com.mapbox.mapboxsdk.maps.MapboxMapimport com.mapbox.mapboxsdk.maps.OnMapReadyCallbackimport com.mapbox.mapboxsdk.maps.Styleimport com.mapbox.mapboxsdk.maps.Style.OnStyleLoadedimport com.mapbox.mapboxsdk.style.layers.LineLayerimport com.mapbox.mapboxsdk.style.layers.Propertyimport com.mapbox.mapboxsdk.style.layers.PropertyFactory.*import com.mapbox.mapboxsdk.style.layers.SymbolLayerimport com.mapbox.mapboxsdk.style.sources.GeoJsonSourceimport com.mapbox.mapboxsdk.utils.BitmapUtilsimport kotlinx.android.synthetic.main.fancygifdialog.*import org.koin.androidx.viewmodel.ext.android.getViewModelimport retrofit2.Callimport retrofit2.Callbackimport retrofit2.Responseimport timber.log.Timberimport java.util.concurrent.TimeUnitclass OrderMapsActivity : BaseActivity() {    lateinit var loadingDialog: LoadingDialog    private val ROUTE_LAYER_ID = "route-layer-id"    private val ROUTE_SOURCE_ID = "route-source-id"    private val ICON_LAYER_ID = "icon-layer-id"    private val ICON_SOURCE_ID = "icon-source-id"    private val RED_PIN_ICON_ID = "red-pin-icon-id"    lateinit var currentRoute: DirectionsRoute    lateinit var client: MapboxDirections    lateinit var origin: Point    lateinit var destination: Point    lateinit var currentLoc: Location    private var orderId: Int = 0    private var orderCode: String = ""    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient    private var longitude = 0.0    private var latitude = 0.0    private var longitudeLaundry = 112.682556    private var latitudeLaundry = -7.5632981    private var address = ""    private val binding: ActivityOrderMapsBinding by binding(R.layout.activity_order_maps)    private val viewModel: TrackingViewModel by lazy {        getViewModel(TrackingViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))        binding.apply {            lifecycleOwner = this@OrderMapsActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()        orderId = intent.getIntExtra("orderId", 0)        orderCode = intent.getStringExtra("orderCode").toString()        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)        checkPermission()        binding.mapView.onCreate(savedInstanceState)        initMaps()        initViewModel()        if (orderCode != "") {            viewModel.getOrderDetail(orderCode)        }        viewModel.getTrackingList(orderId)        binding.ivBack.setSafeOnClickListener {            finish()        }        binding.btnRefresh.setSafeOnClickListener {            if (orderCode != "") {                viewModel.getOrderDetail(orderCode)            }            viewModel.getTrackingList(orderId)        }    }    private fun checkPermission() {        if (ActivityCompat.checkSelfPermission(                this,                Manifest.permission.ACCESS_FINE_LOCATION            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(                this,                Manifest.permission.ACCESS_COARSE_LOCATION            ) != PackageManager.PERMISSION_GRANTED        ) {            ActivityCompat.requestPermissions(                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),                1            )            return        } else {            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->                if (location != null) {                    currentLoc = location                    val geoCoder = Geocoder(this)                    val currentLocation = geoCoder.getFromLocation(                        location.latitude,                        location.longitude,                        1                    )//                    latitude = currentLocation?.first()?.latitude!!//                    longitude = currentLocation?.first()?.longitude!!//                    address = currentLocation?.first()?.getAddressLine(0)!!//                    binding.tvAddress.text = address//                    initMaps()                }            }        }    }    private fun initMaps() {        // Setup the MapView        binding.mapView.getMapAsync(object : OnMapReadyCallback {            override fun onMapReady(@NonNull mapboxMap: MapboxMap) {                mapboxMap.setStyle(Style.MAPBOX_STREETS,                    OnStyleLoaded { style ->                        origin = Point.fromLngLat(longitude, latitude)                        destination = Point.fromLngLat( longitudeLaundry, latitudeLaundry) //laundry                        initSource(style!!)                        initLayers(style)                        // Get the directions route from the Mapbox Directions API                        getRoute(mapboxMap, origin, destination)                    })            }        })    }    private fun initSource(loadedMapStyle: Style) {        loadedMapStyle.addSource(GeoJsonSource(ROUTE_SOURCE_ID))        val iconGeoJsonSource = GeoJsonSource(            ICON_SOURCE_ID, FeatureCollection.fromFeatures(                arrayOf<Feature>(                    Feature.fromGeometry(                        Point.fromLngLat(                            origin!!.longitude(),                            origin!!.latitude()                        )                    ),                    Feature.fromGeometry(                        Point.fromLngLat(                            destination!!.longitude(),                            destination!!.latitude()                        )                    )                )            )        )        loadedMapStyle.addSource(iconGeoJsonSource)    }    private fun initLayers(loadedMapStyle: Style) {        val routeLayer = LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID)        // Add the LineLayer to the map. This layer will display the directions route.        routeLayer.setProperties(            lineCap(Property.LINE_CAP_ROUND),            lineJoin(Property.LINE_JOIN_ROUND),            lineWidth(5f),            lineColor(Color.parseColor("#009688"))        )        loadedMapStyle.addLayer(routeLayer)        // Add the red marker icon image to the map        loadedMapStyle.addImage(            RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(                resources.getDrawable(R.drawable.ic_marker_cust)            )!!        )        // Add the red marker icon SymbolLayer to the map        loadedMapStyle.addLayer(            SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(                iconImage(RED_PIN_ICON_ID),                iconIgnorePlacement(true),                iconAllowOverlap(true),                iconOffset(arrayOf<Float>(0f, -9f))            )        )    }    private fun getRoute(mapboxMap: MapboxMap?, origin: Point, destination: Point) {        client = MapboxDirections.builder()            .origin(origin)            .destination(destination)            .overview(DirectionsCriteria.OVERVIEW_FULL)            .profile(DirectionsCriteria.PROFILE_DRIVING)            .accessToken(getString(R.string.mapbox_access_token))            .build()        client.enqueueCall(object : Callback<DirectionsResponse?> {            override fun onResponse(                call: Call<DirectionsResponse?>,                response: Response<DirectionsResponse?>            ) {                // You can get the generic HTTP info about the response                Timber.d("Response code: " + response.code())                if (response.body() == null) {                    Timber.e("No routes found, make sure you set the right user and access token.")                    return                } else if (response.body()!!.routes().size < 1) {                    Timber.e("No routes found")                    return                }                // Get the directions route                currentRoute = response.body()!!.routes().get(0)                var directiontime = TimeUnit.SECONDS.toMinutes(currentRoute.duration().toLong())                binding.tvTimeDistance.text = "${currentRoute.distance()} - ${directiontime} menit"                mapboxMap?.getStyle(OnStyleLoaded { style ->                    // Retrieve and update the source designated for showing the directions route                    val source = style.getSourceAs<GeoJsonSource>(ROUTE_SOURCE_ID)                    // Create a LineString with the directions route's geometry and                    // reset the GeoJSON source for the route LineLayer source                    source?.setGeoJson(                        LineString.fromPolyline(                            currentRoute.geometry()!!,                            PRECISION_6                        )                    )                })                val pstn = CameraPosition.Builder()                    .target(LatLng(latitude, longitude))                    .zoom(15.0)                    .tilt(20.0)                    .build()                mapboxMap?.animateCamera(                    CameraUpdateFactory.newCameraPosition(pstn),                    1000                )            }            override fun onFailure(call: Call<DirectionsResponse?>, t: Throwable) {                Timber.e("Error: " + t.message)                Toast.makeText(                    this@OrderMapsActivity, "Error: " + t.message,                    Toast.LENGTH_SHORT                ).show()            }        })    }    private fun initViewModel() {        viewModel.trackResponse.observe(this, Observer {            handleWhenTrackSuccess(it)        })        viewModel.orderDetailResponse.observe(this, Observer {            handleWhenDetailSuccess(it)        })        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->            if (showLoading) {                if(loadingDialog != null){                    if(!loadingDialog.isShowLoad())                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    else {                        loadingDialog.dismissDialog()                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")                    }                }            } else {                loadingDialog.dismissDialog()            }        })        viewModel.showError.observe(this, Observer { showError ->            ErrorMessage(this, "", showError)        })    }    private fun handleWhenDetailSuccess(it: OrderDetailResponse?) {        latitude = it?.results?.latitude?.toDouble()!!        longitude = it?.results?.longitude?.toDouble()!!        binding.tvAddress.text = it.results?.addressDescription        initMaps()        if (it.results?.orderItems != null) {            val intent = Intent(this, OrderDetailActivity::class.java)            intent.putExtra("transId", it?.results?.code)            intent.putExtra("orderId", it?.results?.id)            startActivity(intent)            finish()        }    }    private fun handleWhenTrackSuccess(it: TrackingListResponse?) {        if (it?.results?.size != 0 && it?.results != null) {            var dt = it?.results?.last()            latitudeLaundry = dt?.latitude?.toDouble()!!            longitudeLaundry = dt?.longitude?.toDouble()!!            initMaps()            return        }    }    override fun onStart() {        super.onStart()        binding.mapView.onStart()    }    override fun onStop() {        super.onStop()        binding.mapView.onStop()    }    override fun onLowMemory() {        super.onLowMemory()        binding.mapView.onLowMemory()    }    override fun onDestroy() {        super.onDestroy()        binding.mapView.onDestroy()    }}