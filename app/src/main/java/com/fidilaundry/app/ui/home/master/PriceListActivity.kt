package com.fidilaundry.app.ui.home.master

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.viewmodel.MasterViewModel
import com.fidilaundry.app.databinding.ActivityPriceListBinding
import com.fidilaundry.app.model.response.ItemListResponse
import com.fidilaundry.app.ui.base.BaseActivity
import com.fidilaundry.app.ui.home.master.adapter.PriceListAdapter
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.fdialog.ErrorMessage
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.ArrayList

class PriceListActivity : BaseActivity() {

    lateinit var loadingDialog: LoadingDialog

    private var adapter: PriceListAdapter? = null
    private var appList: MutableList<ItemListResponse.Result> = ArrayList()

    private val binding: ActivityPriceListBinding by binding(R.layout.activity_price_list)

    private val viewModel: MasterViewModel by lazy {
        getViewModel(MasterViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@PriceListActivity
            this.vm = viewModel
        }

        loadingDialog = LoadingDialog()

        initViewModel()

        adapter = PriceListAdapter(this)
        binding.rv.layoutManager =
            ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)
        binding.rv.adapter = adapter

        binding.ivBack.setSafeOnClickListener {
            finish()
        }

        binding.extendedFab.setSafeOnClickListener {
            val intent = Intent(this, AddPriceActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        viewModel.itemsListResponse.observe(this, Observer {
            handleWhenListSuccess(it)
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
            ErrorMessage(this, "", showError)
        })
    }

    private fun handleWhenListSuccess(it: ItemListResponse?) {
        appList = it?.results as MutableList<ItemListResponse.Result>
        adapter?.updateList(appList)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getItems()
    }

}