package com.fidilaundry.app.ui.scanner

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.basearch.viewmodel.ScannerViewModel
import com.fidilaundry.app.databinding.ActivityMasterBinding
import com.fidilaundry.app.databinding.ActivityScannerBinding
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.request.SendNotifRequest
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.response.RequestOrderResponse
import com.fidilaundry.app.model.response.UpdateStatusResponse
import com.fidilaundry.app.ui.base.BaseActivity
import com.fidilaundry.app.ui.home.order.AdminOrderActivity
import com.fidilaundry.app.ui.scanner.interfaces.IFClick
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.dialog.DialogOrderAdmin
import com.fidilaundry.app.util.fdialog.ConfirmMessage
import com.fidilaundry.app.util.fdialog.ErrorMessage
import com.fidilaundry.app.util.fdialog.FGCallback
import com.fidilaundry.app.util.fdialog.SuccessMessage
import com.fidilaundry.app.util.setSafeOnClickListener
import com.google.zxing.BarcodeFormat
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.ArrayList

class ScannerActivity : BaseActivity(), IFClick {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog
    lateinit var codeScanner: CodeScanner

    private var transId: String = ""
    private var serviceId: Int = 0

    private val binding: ActivityScannerBinding by binding(R.layout.activity_scanner)

    private val viewModel: ScannerViewModel by lazy {
        getViewModel(ScannerViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@ScannerActivity
            this.vm = viewModel
        }

        loadingDialog = LoadingDialog()
        askPermission()
        initScanner()
        initViewModel()

        binding.scanner.setOnClickListener {
            codeScanner.startPreview()
        }

        binding.ivBack.setSafeOnClickListener {
            finish()
        }

        binding.ivSearch.setSafeOnClickListener {
            val intent = Intent(baseContext, SearchUserActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initViewModel() {
        viewModel.orderResponse.observe(this, Observer {
            handleWhenOrderSuccess(it)
        })

        viewModel.updateOrderStatusResponse.observe(this, Observer {
            handleWhenUpdateSuccess(it)
        })

        viewModel.sendNotifResponse.observe(this, Observer {

        })

        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->
            if (showLoading) {
                if(loadingDialog != null){
                    if(!loadingDialog.isShowLoad())
                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")
                    else {
                        loadingDialog.dismissDialog()
                        loadingDialog.showProgressDialog(this, "Mohon tunggu…")
                    }
                }
            } else {
                loadingDialog.dismissDialog()
            }
        })

        viewModel.showError.observe(this, Observer { showError ->
            when (showError) {
                "errSendNotif" -> {}
                else -> ErrorMessage(this, "", showError)
            }
        })
    }

    private fun handleWhenUpdateSuccess(it: UpdateStatusResponse?) {
        SuccessMessage(this, "Sukses", "Pesanan berhasil ditambahkan!", object : FGCallback {
            override fun onCallback() {
                val intent = Intent(this@ScannerActivity, AdminOrderActivity::class.java)
                intent.putExtra("transId", it?.results?.orderCode)
                intent.putExtra("serviceId", it?.results?.serviceID)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun handleWhenOrderSuccess(it: RequestOrderResponse?) {
        transId = it?.results?.orderCode!!
        serviceId = it?.results?.serviceID!!

        viewModel.sendNotif(
            SendNotifRequest(
                "Pesanan Baru",
                viewModel.custId.value.toInt(),
                "Pesanan Anda telah diproses FIDI Laundry, cek riwayat untuk melihat detail.",
                it?.results?.orderId!!
            )
        )

        viewModel.updateOrderStatus(
            UpdateOrderStatusRequest(
                transId, "cek item", ""
            )
        )
    }

    private fun initScanner() {
        codeScanner = CodeScanner(this, binding.scanner)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                viewModel.custId.value = it.text
                val myRoundedBottomSheet = DialogOrderAdmin(this)
                myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)
            }
        }
        codeScanner.errorCallback = ErrorCallback {
            ErrorMessage(this, "", "Camera initialization error: ${it.message}")
        }
    }

    private fun askPermission() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CAMERA), 1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "You need the camera permission to use this app",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onUserClick(id: Int) {

    }

    override fun onSubmitClick() {
        ConfirmMessage(
            this, "Apakah pelanggan yakin untuk memesan?",
            "", "", "Ya, lanjutkan", "Batal",
            object : FGCallback {
                override fun onCallback() {
                    viewModel.requestOrder(OrderRequest(
                        viewModel.serviceId.value.toInt(), "-7.5629624", "112.6794581",
                        "FIDI Laundry", "Datang Langsung", viewModel.custId.value.toInt(),
                        viewModel.custId.value.toInt()
                    ))
                }
            }
        )
    }
}