package com.fidilaundry.app.ui.home.master

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.basearch.viewmodel.MasterViewModel
import com.fidilaundry.app.databinding.ActivityItemListBinding
import com.fidilaundry.app.databinding.ActivityMasterBinding
import com.fidilaundry.app.databinding.ActivityUserListBinding
import com.fidilaundry.app.model.response.CategoryListResponse
import com.fidilaundry.app.model.response.ServiceListResponse
import com.fidilaundry.app.ui.base.BaseActivity
import com.fidilaundry.app.ui.home.master.adapter.ItemListAdapter
import com.fidilaundry.app.ui.home.master.adapter.UserListAdapter
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.dialog.DialogAddData
import com.fidilaundry.app.util.fdialog.ConfirmMessage
import com.fidilaundry.app.util.fdialog.ErrorMessage
import com.fidilaundry.app.util.fdialog.FGCallback
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.ArrayList

class ItemListActivity : BaseActivity() {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    private var serviceList: MutableList<String> = ArrayList()
    private var categoryList: MutableList<String> = ArrayList()

    private var adapter: ItemListAdapter? = null

    private val binding: ActivityItemListBinding by binding(R.layout.activity_item_list)

    private val viewModel: MasterViewModel by lazy {
        getViewModel(MasterViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@ItemListActivity
            this.vm = viewModel
        }

        loadingDialog = LoadingDialog()

        initViewModel()

        var type = intent.getStringExtra("type").toString()
        if (type == "service") {
            binding.tvTitle.text = "Data "
            viewModel.getService()
        } else {
            binding.tvTitle.text = "Data Jenis Cuci"
            viewModel.getCategory()
        }

        adapter = ItemListAdapter(this)
        binding.rv.layoutManager =
            ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(ListDivideritemDecoration(this))
//        adapter.updateList(appList)

        binding.ivBack.setSafeOnClickListener {
            finish()
        }

        binding.extendedFab.setSafeOnClickListener {
            val myRoundedBottomSheet = DialogAddData(1)
            myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)
        }
    }

    private fun initViewModel() {
        viewModel.serviceResponse.observe(this, Observer {
            handleWhenServiceSuccess(it)
        })

        viewModel.categoryResponse.observe(this, Observer {
            handleWhenCategorySuccess(it)
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

    private fun handleWhenServiceSuccess(it: ServiceListResponse?) {
        for (i in it?.results?.indices!!) {
            serviceList.add(it.results!![i].title!!)
        }
        adapter?.updateList(serviceList)
    }

    private fun handleWhenCategorySuccess(it: CategoryListResponse?) {
        for (i in it?.results?.indices!!) {
            categoryList.add(it.results!![i].title!!)
        }
        adapter?.updateList(categoryList)
    }
}