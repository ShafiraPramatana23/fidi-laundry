package com.fidilaundry.app.ui.home.master

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.CustomerViewModel
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.basearch.viewmodel.MasterViewModel
import com.fidilaundry.app.databinding.ActivityMasterBinding
import com.fidilaundry.app.databinding.ActivityUserListBinding
import com.fidilaundry.app.model.response.CustomerListResponse
import com.fidilaundry.app.ui.base.BaseActivity
import com.fidilaundry.app.ui.home.master.adapter.PriceListAdapter
import com.fidilaundry.app.ui.home.master.adapter.UserListAdapter
import com.fidilaundry.app.ui.scanner.interfaces.IFClick
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.fdialog.ConfirmMessage
import com.fidilaundry.app.util.fdialog.ErrorMessage
import com.fidilaundry.app.util.fdialog.FGCallback
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.ArrayList

class UserListActivity : BaseActivity(), IFClick {

    lateinit var loadingDialog: LoadingDialog

    private var adapter: UserListAdapter? = null

    private val binding: ActivityUserListBinding by binding(R.layout.activity_user_list)

    private val viewModel: CustomerViewModel by lazy {
        getViewModel(CustomerViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@UserListActivity
            this.vm = viewModel
        }

        loadingDialog = LoadingDialog()

        initViewModel()

        adapter = UserListAdapter(this, 2, this)
        binding.rv.layoutManager =
            ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(ListDivideritemDecoration(this))

        binding.ivBack.setSafeOnClickListener {
            finish()
        }

        binding.extendedFab.setSafeOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            intent.putExtra("type", "1")
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        viewModel.custResponse.observe(this, Observer {
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

    private fun handleWhenListSuccess(it: CustomerListResponse?) {
        adapter?.updateList(it?.results!!)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCustomer()
    }

    override fun onUserClick(id: Int) {
    }

    override fun onSubmitClick() {
    }
}