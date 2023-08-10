package com.fidilaundry.app.ui.scanner

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.viewmodel.CustomerViewModel
import com.fidilaundry.app.basearch.viewmodel.ScannerViewModel
import com.fidilaundry.app.databinding.ActivitySearchUserBinding
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.CustomerListResponse
import com.fidilaundry.app.ui.base.BaseActivity
import com.fidilaundry.app.ui.home.master.adapter.UserListAdapter
import com.fidilaundry.app.ui.home.order.AdminOrderActivity
import com.fidilaundry.app.ui.scanner.interfaces.IFClick
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.dialog.DialogOrderAdmin
import com.fidilaundry.app.util.dialog.DialogService
import com.fidilaundry.app.util.fdialog.ConfirmMessage
import com.fidilaundry.app.util.fdialog.ErrorMessage
import com.fidilaundry.app.util.fdialog.FGCallback
import com.fidilaundry.app.util.fdialog.SuccessMessage
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.ArrayList

class SearchUserActivity : BaseActivity(), IFClick {

    lateinit var loadingDialog: LoadingDialog

    private var adapter: UserListAdapter? = null
    private var data: List<CustomerListResponse.Result> = ArrayList()

    private val binding: ActivitySearchUserBinding by binding(R.layout.activity_search_user)

    private val viewModel: ScannerViewModel by lazy {
        getViewModel(ScannerViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@SearchUserActivity
            this.vm = viewModel
        }

        loadingDialog = LoadingDialog()

        initViewModel()

        adapter = UserListAdapter(this, 1, this)
        binding.rv.layoutManager =
            ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(ListDivideritemDecoration(this))

        binding.ivBack.setSafeOnClickListener {
            finish()
        }

        binding.etSearch.addTextChangedListener(
            object : TextWatcher {
                override fun onTextChanged(s: CharSequence,start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(s: CharSequence,start: Int,count: Int, after: Int) {}
                // Milliseconds
                override fun afterTextChanged(s: Editable) {
                    if(s.isEmpty()){
//                        pulsaAdapter?.updateList(listData, selectedVal, dialogType)
                    } else{
                        doFilter(s.toString())
                    }
                }
            }
        )
    }

    fun doFilter(keySearch:String){
        val listFiltered = data.filter { it.name?.contains(keySearch,true) == true }
        adapter!!.updateList(listFiltered)
    }

    private fun initViewModel() {
        viewModel.custResponse.observe(this, Observer {
            handleWhenListSuccess(it)
        })

        viewModel.orderResponse.observe(this, Observer {
            handleWhenRequestOrderSuccess(it)
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

    private fun handleWhenRequestOrderSuccess(it: BaseResponse?) {
        SuccessMessage(this, "Sukses", "Pesanan berhasil ditambahkan!", object : FGCallback {
            override fun onCallback() {
                val intent = Intent(this@SearchUserActivity, AdminOrderActivity::class.java)
//                intent.putExtra("transId", app.code)
//                intent.putExtra("serviceId", app.serviceID)
                startActivity(intent)
            }
        })
    }

    private fun handleWhenListSuccess(it: CustomerListResponse?) {
        data = it?.results!!
        adapter?.updateList(it?.results!!)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCustomer()
    }

    override fun onUserClick(id: Int) {
        viewModel.custId.value = id.toString()
        val myRoundedBottomSheet = DialogOrderAdmin(this)
        myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)
    }

    override fun onSubmitClick() {
        ConfirmMessage(
            this, "Apakah pelanggan yakin untuk memesan?",
            "", "", "Ya, lanjutkan", "Batal",
            object : FGCallback {
                override fun onCallback() {
                    viewModel.requestOrder(OrderRequest(
                        viewModel.serviceId.value.toInt(), "-7.5629624", "112.6794581",
                        "mana aja", "Antar Jemput", viewModel.custId.value.toInt()
                    ))
                }
            }
        )
    }
}