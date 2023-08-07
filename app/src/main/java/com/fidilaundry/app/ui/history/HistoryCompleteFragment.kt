package com.fidilaundry.app.ui.history

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.ui.base.BaseFragment
import com.fidilaundry.app.util.LoadingDialog
import org.koin.androidx.viewmodel.ext.android.getViewModel
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.basearch.viewmodel.HistoryViewModel
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.databinding.FragmentHistoryDataBinding
import com.fidilaundry.app.databinding.FragmentHomeBinding
import com.fidilaundry.app.model.response.OrderListResponse
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.ui.history.adapter.HistoryAdapter
import com.fidilaundry.app.ui.history.model.HistoryData
import com.fidilaundry.app.ui.home.order.UserOrderActivity
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.fdialog.ErrorMessage
import com.fidilaundry.app.util.setSafeOnClickListener

class HistoryCompleteFragment : BaseFragment() {
    lateinit var loadingDialog: LoadingDialog
    lateinit var paperPrefs: PaperPrefs

    private var adapter: HistoryAdapter? = null
    private var profileData: ProfileResponse.Results? = null

    private val viewModel: HistoryViewModel by lazy {
        getViewModel(HistoryViewModel::class)
    }

    private lateinit var vie: View
    private var _binding: FragmentHistoryDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryDataBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner = this@HistoryCompleteFragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        profileData = paperPref.getDataProfile()

        adapter = HistoryAdapter(context)
        binding.rv.adapter = adapter
        binding.rv.layoutManager =
            ScrollingLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false, 0)
        binding.rv.addItemDecoration(ListDivideritemDecoration(requireContext()))
//        adapter?.updateList(historyList)
    }

    private fun initViewModel() {
        viewModel.orderListResponse.observe(this, Observer {
            handleWhenListSuccess(it)
        })

        viewModel.orderListCustResponse.observe(this, Observer {
            handleWhenListCustSuccess(it)
        })

        viewModel.showProgressLiveData.observe(this, Observer { showLoading ->
            if (showLoading) {
                if(loadingDialog != null){
                    if(!loadingDialog.isShowLoad())
                        loadingDialog.showProgressDialog(requireActivity(), "Mohon tunggu…")
                    else {
                        loadingDialog.dismissDialog()
                        loadingDialog.showProgressDialog(requireActivity(), "Mohon tunggu…")
                    }
                }
            } else {
                loadingDialog.dismissDialog()
            }
        })

        viewModel.showError.observe(this, Observer { showError ->
            ErrorMessage(requireActivity(), "", showError)
        })
    }

    private fun handleWhenListCustSuccess(it: OrderListResponse?) {
        adapter?.updateList(it?.results!!)

        if (it?.results?.size != 0) {
            binding.llEmpty .visibility = View.GONE
        } else {
            binding.llEmpty.visibility = View.VISIBLE
        }
    }

    private fun handleWhenListSuccess(it: OrderListResponse?) {
        adapter?.updateList(it?.results!!)

        if (it?.results?.size != 0) {
            binding.llEmpty .visibility = View.GONE
        } else {
            binding.llEmpty.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        if (profileData?.role == "customer" || profileData?.role == "member") {
            viewModel.getOrderListCust("", "", "", "selesai")
        } else {
            viewModel.getOrderList("", "", "", "")
        }
    }

    companion object {
        fun newInstance(title: String): Fragment {
            val fragment = HistoryCompleteFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }}
}