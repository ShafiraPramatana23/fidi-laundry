package com.fidilaundry.app.uiimport android.annotation.TargetApiimport android.app.Activityimport android.content.Intentimport android.os.Buildimport android.os.Bundleimport android.os.CountDownTimerimport android.view.Viewimport android.view.WindowManagerimport androidx.core.content.ContextCompatimport com.fidilaundry.app.Rimport com.fidilaundry.app.ui.base.BaseActivityimport com.fidilaundry.app.ui.auth.LoginActivityclass SplashActivity : BaseActivity() {    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        setContentView(R.layout.activity_splash)        setStatusBarGradient(this)        object : CountDownTimer(1500, 1000) {            override fun onFinish() {                if (paperPref.getDataProfile() != null) {                    intent = Intent(baseContext, MainActivity::class.java)                } else {                    intent = Intent(baseContext, LoginActivity::class.java)                }                startActivity(intent)                finish()            }            override fun onTick(millisUntilFinished: Long) {}        }.start()    }    companion object {        @TargetApi(Build.VERSION_CODES.LOLLIPOP)        fun setStatusBarGradient(activity: Activity) {            val window = activity.window            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)            window.statusBarColor = ContextCompat.getColor(activity, android.R.color.white)            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR        }    }}