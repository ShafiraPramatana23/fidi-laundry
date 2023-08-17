package com.fidilaundry.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.databinding.FragmentHomeBinding
import com.fidilaundry.app.model.response.OrderListResponse
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.ui.base.BaseFragment
import com.fidilaundry.app.ui.complaint.ComplaintActivity
import com.fidilaundry.app.ui.complaint.UserComplaintActivity
import com.fidilaundry.app.ui.history.adapter.HistoryAdapter
import com.fidilaundry.app.ui.history.model.HistoryData
import com.fidilaundry.app.ui.home.master.MasterActivity
import com.fidilaundry.app.ui.home.order.*
import com.fidilaundry.app.ui.scanner.ScannerActivity
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.fdialog.ErrorMessage
import com.fidilaundry.app.util.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home_admin.view.*
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeFragment : BaseFragment() {
    lateinit var loadingDialog: LoadingDialog

    private var historyAdapter: HistoryAdapter? = null

    private var profileData: ProfileResponse.Results? = null

    private val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner = this@HomeFragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()

        profileData = paperPref.getDataProfile()
        binding.tvName.text = profileData?.name

        historyAdapter = HistoryAdapter(context)

        initViewModel()

        if (profileData?.role == "customer" || profileData?.role == "member") {
            layoutUser()
        } else {
            layoutAdmin()
        }
    }

    // ADMIN
    private fun layoutAdmin() {
        binding.layoutAdmin.visibility = View.VISIBLE
        binding.layoutUser.visibility = View.GONE

        var layAdmin = binding.layoutAdmin

        layAdmin.rvActiveOrder.adapter = historyAdapter
        layAdmin.rvActiveOrder.layoutManager =
            ScrollingLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false, 0)
        layAdmin.rvActiveOrder.addItemDecoration(ListDivideritemDecoration(requireContext()))
//        historyAdapter?.updateList(historyList)

        viewModel.getOrderList("", "", "", "")

        layAdmin.btnMaster.setSafeOnClickListener {
            activity?.intent = Intent(activity, MasterActivity::class.java)
            startActivity(activity?.intent)
        }

        layAdmin.btnConfirm.setSafeOnClickListener {
            activity?.intent = Intent(activity, OrderListActivity::class.java)
            startActivity(activity?.intent)
        }

        layAdmin.btnComplaint.setSafeOnClickListener {
            activity?.intent = Intent(activity, ComplaintActivity::class.java)
            startActivity(activity?.intent)
        }

        layAdmin.tvSeeAllAdmin.setSafeOnClickListener {
            activity?.intent = Intent(activity, OrderListActivity::class.java)
            startActivity(activity?.intent)
        }

        layAdmin.tvOrder.setSafeOnClickListener {
            activity?.intent = Intent(activity, AdminOrderActivity::class.java)
            startActivity(activity?.intent)
        }
    }

    private val historyList: List<HistoryData>
        get() {
            val appList: MutableList<HistoryData> = ArrayList()
            appList.add(HistoryData("Setrika", "15 Jun 2022 11:11", 20000.0))
            appList.add(HistoryData("Cuci Setrika", "15 Jun 2022 11:11", 20000.0))
            appList.add(HistoryData("Setrika", "15 Jun 2022 11:11", 20000.0))
            return appList
        }

    // USER
    private fun layoutUser() {
        binding.layoutAdmin.visibility = View.GONE
        binding.layoutUser.visibility = View.VISIBLE

        rvActive.adapter = historyAdapter
        rvActive.layoutManager =
            ScrollingLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false, 0)
        rvActive.addItemDecoration(ListDivideritemDecoration(requireContext()))

        viewModel.getOrderListCust("", "", "", "")

        binding.btnSetrika.setSafeOnClickListener {
            activity?.intent = Intent(activity, UserOrderActivity::class.java)
            activity?.intent?.putExtra("idService", 4)
            startActivity(activity?.intent)
        }

        binding.btnCusek.setSafeOnClickListener {
            activity?.intent = Intent(activity, UserOrderActivity::class.java)
            activity?.intent?.putExtra("idService", 3)
            startActivity(activity?.intent)
        }

        binding.btnCuba.setSafeOnClickListener {
            activity?.intent = Intent(activity, AdminOrderMapsActivity::class.java)
//            activity?.intent?.putExtra("idService", 2)
            startActivity(activity?.intent)
        }

        binding.btnCuker.setSafeOnClickListener {
            activity?.intent = Intent(activity, UserOrderActivity::class.java)
            activity?.intent?.putExtra("idService", 1)
            startActivity(activity?.intent)
        }
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
        historyAdapter?.updateList(it?.results!!)

        if (it?.results?.size != 0) {
            binding.llEmpty .visibility = View.GONE
        } else {
            binding.llEmpty.visibility = View.VISIBLE
        }
    }

    private fun handleWhenListSuccess(it: OrderListResponse?) {
        historyAdapter?.updateList(it?.results!!)

        if (profileData?.role == "customer" || profileData?.role == "member") {
            if (it?.results?.size != 0) {
                binding.llEmpty .visibility = View.GONE
            } else {
                binding.llEmpty.visibility = View.VISIBLE
            }
        } else {
            var layAdmin = binding.layoutAdmin
            if (it?.results?.size != 0) {
                layAdmin.llEmptyAdmin.visibility = View.GONE
            } else {
                layAdmin.llEmptyAdmin.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOrderList("", "", "", "")
    }

    companion object {
        fun newInstance(title: String): Fragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }
    }
}