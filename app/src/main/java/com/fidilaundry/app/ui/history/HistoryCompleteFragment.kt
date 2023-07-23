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
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.databinding.FragmentHistoryDataBinding
import com.fidilaundry.app.databinding.FragmentHomeBinding
import com.fidilaundry.app.ui.history.adapter.HistoryAdapter
import com.fidilaundry.app.ui.history.model.HistoryData
import com.fidilaundry.app.ui.home.order.UserOrderActivity
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.setSafeOnClickListener

class HistoryCompleteFragment : BaseFragment() {
    lateinit var loadingDialog: LoadingDialog
    lateinit var paperPrefs: PaperPrefs

    private var adapter: HistoryAdapter? = null

    private val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
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

        adapter = HistoryAdapter(context)
        binding.rv.adapter = adapter
        binding.rv.layoutManager =
            ScrollingLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false, 0)
        binding.rv.addItemDecoration(ListDivideritemDecoration(requireContext()))
//        adapter?.updateList(historyList)
    }

    private val historyList: List<HistoryData>
        get() {
            val appList: MutableList<HistoryData> = ArrayList()
            appList.add(HistoryData("Setrika", "15 Jun 2022 11:11", 20000.0))
            appList.add(HistoryData("Cuci Setrika", "15 Jun 2022 11:11", 20000.0))
            appList.add(HistoryData("Setrika", "15 Jun 2022 11:11", 20000.0))
            return appList
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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