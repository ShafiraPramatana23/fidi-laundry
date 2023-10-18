package com.fidilaundry.app.ui.scanner

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.fidilaundry.app.ui.base.BaseActivity
import com.fidilaundry.app.ui.home.master.adapter.MasterMenuAdapter
import com.fidilaundry.app.ui.home.master.adapter.PriceListAdapter
import com.fidilaundry.app.ui.home.master.model.MasterMenu
import com.fidilaundry.app.ui.home.order.OrderMapsActivity
import com.fidilaundry.app.ui.scanner.interfaces.IFClick
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.dialog.DialogOrderAdmin
import com.fidilaundry.app.util.fdialog.ConfirmMessage
import com.fidilaundry.app.util.fdialog.FGCallback
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.ArrayList

class ScannerActivity : BaseActivity(), IFClick {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog
    lateinit var codeScanner: CodeScanner

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

        askPermission()
        initScanner()

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

    private fun initScanner() {
        codeScanner = CodeScanner(this, binding.scanner)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()

                viewModel.custId.value = it.text
                val myRoundedBottomSheet = DialogOrderAdmin(this)
                myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
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
                        "mana aja", "Datang Langsung", viewModel.custId.value.toInt(),
                        viewModel.custId.value.toInt()
                    ))
                }
            }
        )
    }
}