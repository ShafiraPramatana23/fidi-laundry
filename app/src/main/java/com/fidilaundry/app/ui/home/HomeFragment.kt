package com.fidilaundry.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.databinding.FragmentHomeBinding
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.ui.base.BaseFragment
import com.fidilaundry.app.ui.complaint.ComplaintActivity
import com.fidilaundry.app.ui.complaint.UserComplaintActivity
import com.fidilaundry.app.ui.history.adapter.HistoryAdapter
import com.fidilaundry.app.ui.history.model.HistoryData
import com.fidilaundry.app.ui.home.master.MasterActivity
import com.fidilaundry.app.ui.home.order.AdminOrderActivity
import com.fidilaundry.app.ui.home.order.OrderListActivity
import com.fidilaundry.app.ui.home.order.UserOrderActivity
import com.fidilaundry.app.ui.scanner.ScannerActivity
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home_admin.view.*
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeFragment : BaseFragment() {
    lateinit var loadingDialog: LoadingDialog
    lateinit var paperPrefs: PaperPrefs

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
        paperPrefs = PaperPrefs(requireContext())

        profileData = paperPrefs.getDataProfile()
        binding.tvName.text = profileData?.name

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

        historyAdapter = HistoryAdapter(context)
        layAdmin.rvActiveOrder.adapter = historyAdapter
        layAdmin.rvActiveOrder.layoutManager =
            ScrollingLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false, 0)
        layAdmin.rvActiveOrder.addItemDecoration(ListDivideritemDecoration(requireContext()))
        historyAdapter?.updateList(historyList)

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
//            layAdmin.visibility = View.GONE
//            binding.layoutUser.visibility = View.VISIBLE
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

        binding.btnSetrika.setSafeOnClickListener {
            activity?.intent = Intent(activity, UserOrderActivity::class.java)
            startActivity(activity?.intent)
        }

        binding.btnCusek.setSafeOnClickListener {
            activity?.intent = Intent(activity, UserComplaintActivity::class.java)
            startActivity(activity?.intent)
        }

        binding.btnCuba.setSafeOnClickListener {
            binding.layoutAdmin.visibility = View.GONE
            binding.layoutUser.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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