package com.fidilaundry.app.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import retrofit2.HttpException
import timber.log.Timber
import kotlin.reflect.KClass
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.viewmodel.BaseViewModel
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.fdialog.ErrorMessage
import com.fidilaundry.app.util.autoCleared
import com.fidilaundry.app.ext.withNotNull
import org.koin.androidx.viewmodel.ext.android.getViewModel

abstract class BaseActivity<B : ViewDataBinding, M : BaseViewModel> : AppCompatActivity() {

    protected abstract val layoutId: Int
    protected abstract val navControllerId: Int
    protected abstract val viewModelClass: KClass<M>
    private var binding: B by autoCleared()
    protected val navController: NavController
            by lazy(LazyThreadSafetyMode.NONE) { Navigation.findNavController(this, navControllerId) }
    protected val viewModel: M by lazy(LazyThreadSafetyMode.NONE) { getViewModel(viewModelClass) }
    protected abstract val observeLiveData: M.() -> Unit

    private var _progressDialog: LoadingDialog? = null
    private var _progressDialogsCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        observeBaseLiveData()
    }

    override fun onResume() {
        super.onResume()
        with(viewModel.alertMessageLiveData) {
            value?.let {
                this@BaseActivity.parseError(it)
                value = null
            }
        }
    }

    override fun onDestroy() {
        _progressDialogsCount = 0
        _progressDialog = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        if ((getCurFragment(navControllerId) as? ICanBackPress)?.onBackPressed() != true) {
            super.onBackPressed()
        }
    }

    private fun parseError(it: Throwable) {
        val message = it.localizedMessage
        if (it is HttpException) {
            when ((it).code()) {
//                HttpURLConnection.HTTP_BAD_REQUEST -> toast("Error: ${it.message()}")
//                HttpURLConnection.HTTP_UNAUTHORIZED -> startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
//                HttpURLConnection.HTTP_FORBIDDEN -> startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
//                HttpURLConnection.HTTP_NOT_FOUND -> ServerErrorDialog().showDialog(this)
//                HttpURLConnection.HTTP_INTERNAL_ERROR -> ServerErrorDialog().showDialog(this)
//                else -> ServerErrorDialog().showDialog(this)
            }
        } else {
            Timber.e("Error: ${it.localizedMessage}")
        }

        showAlert(message)
    }

    private fun observeBaseLiveData() = with(viewModel) {
        showProgressLiveData.observe(this@BaseActivity, Observer { showProgress ->
            showProgress?.let { if (it) this@BaseActivity.showProgress(getString(R.string.loadingWithThreeDots)) else this@BaseActivity.hideProgress() }
        })
        alertMessageLiveData.observe(this@BaseActivity, Observer { alertMessage ->
            alertMessage?.let {
                this@BaseActivity.parseError(it)
                alertMessageLiveData.value = null
            }
        })

        observeLiveData()
    }

    fun showProgress(text: String?) {
        _progressDialogsCount++
        val inShow = _progressDialog != null
        if (!inShow) _progressDialog = LoadingDialog()
        withNotNull(_progressDialog) {
            progressDialog(this@BaseActivity, text!!)
        }
    }

    fun hideProgress() {
        _progressDialogsCount--
        withNotNull(_progressDialog) {
            if (_progressDialogsCount == 0) {
                dismissDialog()
                _progressDialog = null
            }
        }
    }

    fun showAlert(text: String) {
        ErrorMessage(this, "", text)
    }

    private fun getCurFragment(@IdRes containerId: Int) = supportFragmentManager.findFragmentById(containerId)?.let { getCurFragment(it) }

    private fun getCurFragment(navHostFragment: Fragment): Fragment = navHostFragment.childFragmentManager.fragments[0]

}