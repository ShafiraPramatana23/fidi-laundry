package com.fidilaundry.app.ui.home.orderimport android.content.Intentimport android.graphics.Colorimport android.os.Bundleimport android.widget.Toastimport androidx.annotation.NonNullimport com.fidilaundry.app.Rimport com.fidilaundry.app.basearch.viewmodel.OrderViewModelimport com.fidilaundry.app.databinding.ActivityOrderMapsBindingimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.util.LoadingDialogimport com.fidilaundry.app.util.LocationPermissionHelperimport com.fidilaundry.app.util.fdialog.ErrorMessageimport com.fidilaundry.app.util.setSafeOnClickListenerimport com.mapbox.api.directions.v5.DirectionsCriteriaimport com.mapbox.api.directions.v5.MapboxDirectionsimport com.mapbox.api.directions.v5.models.DirectionsResponseimport com.mapbox.api.directions.v5.models.DirectionsRouteimport com.mapbox.core.constants.Constants.PRECISION_6import com.mapbox.geojson.Featureimport com.mapbox.geojson.FeatureCollectionimport com.mapbox.geojson.LineStringimport com.mapbox.geojson.Pointimport com.mapbox.mapboxsdk.Mapboximport com.mapbox.mapboxsdk.maps.MapboxMapimport com.mapbox.mapboxsdk.maps.OnMapReadyCallbackimport com.mapbox.mapboxsdk.maps.Styleimport com.mapbox.mapboxsdk.maps.Style.OnStyleLoadedimport com.mapbox.mapboxsdk.style.layers.LineLayerimport com.mapbox.mapboxsdk.style.layers.Propertyimport com.mapbox.mapboxsdk.style.layers.PropertyFactory.*import com.mapbox.mapboxsdk.style.layers.SymbolLayerimport com.mapbox.mapboxsdk.style.sources.GeoJsonSourceimport com.mapbox.mapboxsdk.utils.BitmapUtilsimport org.koin.androidx.viewmodel.ext.android.getViewModelimport retrofit2.Callimport retrofit2.Callbackimport retrofit2.Responseimport timber.log.Timberimport java.util.concurrent.TimeUnitclass OrderMapsActivity : BaseActivity() {    lateinit var loadingDialog: LoadingDialog    private val ROUTE_LAYER_ID = "route-layer-id"    private val ROUTE_SOURCE_ID = "route-source-id"    private val ICON_LAYER_ID = "icon-layer-id"    private val ICON_SOURCE_ID = "icon-source-id"    private val RED_PIN_ICON_ID = "red-pin-icon-id"    lateinit var currentRoute: DirectionsRoute    lateinit var client: MapboxDirections    lateinit var origin: Point    lateinit var destination: Point    private val binding: ActivityOrderMapsBinding by binding(R.layout.activity_order_maps)    private val viewModel: OrderViewModel by lazy {        getViewModel(OrderViewModel::class)    }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))        binding.apply {            lifecycleOwner = this@OrderMapsActivity            this.vm = viewModel        }        loadingDialog = LoadingDialog()//        binding.mapView.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)        binding.ivBack.setSafeOnClickListener {            finish()        }        binding.mapView.onCreate(savedInstanceState)        // Setup the MapView        binding.mapView.getMapAsync(object : OnMapReadyCallback {            override fun onMapReady(@NonNull mapboxMap: MapboxMap) {                mapboxMap.setStyle(Style.MAPBOX_STREETS,                    OnStyleLoaded { style ->                        origin = Point.fromLngLat( 112.6794581, -7.5629624)                        destination = Point.fromLngLat( 112.7102763, -7.4568788)                        mapboxMap.cameraPosition.target.latitude = -7.5629624                        mapboxMap.cameraPosition.target.longitude = 112.6794581                        initSource(style!!)                        initLayers(style)                        // Get the directions route from the Mapbox Directions API                        getRoute(mapboxMap, origin, destination)                    })            }        })        binding.btnRefresh.setSafeOnClickListener {            // get detail order -> update marker petugas laundry            val intent = Intent(baseContext, OrderDetailActivity::class.java)            startActivity(intent)        }    }    private fun initSource(loadedMapStyle: Style) {        loadedMapStyle.addSource(GeoJsonSource(ROUTE_SOURCE_ID))        val iconGeoJsonSource = GeoJsonSource(            ICON_SOURCE_ID, FeatureCollection.fromFeatures(                arrayOf<Feature>(                    Feature.fromGeometry(                        Point.fromLngLat(                            origin!!.longitude(),                            origin!!.latitude()                        )                    ),                    Feature.fromGeometry(                        Point.fromLngLat(                            destination!!.longitude(),                            destination!!.latitude()                        )                    )                )            )        )        loadedMapStyle.addSource(iconGeoJsonSource)    }    private fun initLayers(loadedMapStyle: Style) {        val routeLayer = LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID)        // Add the LineLayer to the map. This layer will display the directions route.        routeLayer.setProperties(            lineCap(Property.LINE_CAP_ROUND),            lineJoin(Property.LINE_JOIN_ROUND),            lineWidth(5f),            lineColor(Color.parseColor("#009688"))        )        loadedMapStyle.addLayer(routeLayer)        // Add the red marker icon image to the map        loadedMapStyle.addImage(            RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(                resources.getDrawable(R.drawable.ic_marker_cust)            )!!        )        // Add the red marker icon SymbolLayer to the map        loadedMapStyle.addLayer(            SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(                iconImage(RED_PIN_ICON_ID),                iconIgnorePlacement(true),                iconAllowOverlap(true),                iconOffset(arrayOf<Float>(0f, -9f))            )        )    }    private fun getRoute(mapboxMap: MapboxMap?, origin: Point, destination: Point) {        client = MapboxDirections.builder()            .origin(origin)            .destination(destination)            .overview(DirectionsCriteria.OVERVIEW_FULL)            .profile(DirectionsCriteria.PROFILE_DRIVING)            .accessToken(getString(R.string.mapbox_access_token))            .build()        client.enqueueCall(object : Callback<DirectionsResponse?> {            override fun onResponse(                call: Call<DirectionsResponse?>,                response: Response<DirectionsResponse?>            ) {                // You can get the generic HTTP info about the response                Timber.d("Response code: " + response.code())                if (response.body() == null) {                    Timber.e("No routes found, make sure you set the right user and access token.")                    return                } else if (response.body()!!.routes().size < 1) {                    Timber.e("No routes found")                    return                }                // Get the directions route                currentRoute = response.body()!!.routes().get(0)                var directiontime = TimeUnit.SECONDS.toMinutes(currentRoute.duration().toLong())                binding.tvTimeDistance.text = "${currentRoute.distance()} - ${directiontime} menit"                mapboxMap?.getStyle(OnStyleLoaded { style ->                    // Retrieve and update the source designated for showing the directions route                    val source = style.getSourceAs<GeoJsonSource>(ROUTE_SOURCE_ID)                    // Create a LineString with the directions route's geometry and                    // reset the GeoJSON source for the route LineLayer source                    source?.setGeoJson(                        LineString.fromPolyline(                            currentRoute.geometry()!!,                            PRECISION_6                        )                    )                })            }            override fun onFailure(call: Call<DirectionsResponse?>, t: Throwable) {                Timber.e("Error: " + t.message)                Toast.makeText(                    this@OrderMapsActivity, "Error: " + t.message,                    Toast.LENGTH_SHORT                ).show()            }        })    }    override fun onStart() {        super.onStart()        binding.mapView.onStart()    }    override fun onStop() {        super.onStop()        binding.mapView.onStop()    }    override fun onLowMemory() {        super.onLowMemory()        binding.mapView.onLowMemory()    }    override fun onDestroy() {        super.onDestroy()        binding.mapView.onDestroy()    }}