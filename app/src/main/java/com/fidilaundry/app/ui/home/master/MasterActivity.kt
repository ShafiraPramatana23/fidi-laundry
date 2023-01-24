package com.fidilaundry.app.ui.home.master

import android.os.Bundle
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.databinding.ActivityMasterBinding
import com.fidilaundry.app.ui.base.BaseActivity
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MasterActivity : BaseActivity() {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    private val binding: ActivityMasterBinding by binding(R.layout.activity_master)

    private val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MasterActivity
            this.vm = viewModel
        }

        binding.ivBack.setSafeOnClickListener {
            finish()
        }
    }
}