package com.fidilaundry.app.uiimport android.content.Intentimport android.os.Bundleimport android.os.Handlerimport android.widget.Toastimport androidx.navigation.NavControllerimport androidx.navigation.fragment.NavHostFragmentimport androidx.navigation.ui.NavigationUIimport com.fidilaundry.app.Rimport com.fidilaundry.app.databinding.ActivityMainBindingimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.ui.scanner.ScannerActivityimport com.fidilaundry.app.util.dialog.DialogDropdownimport com.fidilaundry.app.util.dialog.DialogShowQRimport com.fidilaundry.app.util.setSafeOnClickListenerclass MainActivity : BaseActivity() {    lateinit var navHostFragment: NavHostFragment    var doubleBackToExitPressedOnce = false    var lastFragment: Int = 0    private lateinit var binding: ActivityMainBinding    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        binding = ActivityMainBinding.inflate(layoutInflater)        setContentView(binding.root)        setUpNavigation()        lastFragment = R.id.home        binding.fab.setSafeOnClickListener {            var profileData = paperPref.getDataProfile()            if (profileData?.role != "admin") {                val myRoundedBottomSheet = DialogShowQR()                myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)            } else {                val intent = Intent(this, ScannerActivity::class.java)                startActivity(intent)            }        }    }    private fun setUpNavigation() {        navHostFragment =            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment        val navController: NavController = navHostFragment.navController        NavigationUI.setupWithNavController(            binding.bnve, navController        )        binding.bnve.setOnNavigationItemSelectedListener { item ->            when (item.itemId) {                lastFragment -> false                else -> {                    navController.navigate(item.itemId)                    lastFragment = item.itemId                    true                }            }        }        binding.bnve.itemIconTintList = null    }    override fun onBackPressed() {        lastFragment = 0        val screen = navHostFragment.navController.currentDestination!!.label        if (screen?.equals("HomeFragment")!!) {            if (!doubleBackToExitPressedOnce) {                this.doubleBackToExitPressedOnce = true                Toast.makeText(this, "Tekan kembali lagi untuk keluar", Toast.LENGTH_SHORT)                    .show()                Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)            } else {                super.onBackPressed()            }        } else {            super.onBackPressed()        }    }}