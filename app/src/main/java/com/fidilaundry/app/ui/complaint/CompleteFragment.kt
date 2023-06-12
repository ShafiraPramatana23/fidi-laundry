package com.fidilaundry.app.ui.complaint

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.MasterViewModel
import com.fidilaundry.app.databinding.FragmentComplaintCompleteBinding
import com.fidilaundry.app.databinding.FragmentComplaintInprogressBinding
import com.fidilaundry.app.ui.complaint.adapter.ComplaintListAdapter
import com.fidilaundry.app.ui.home.master.adapter.ItemListAdapter
import com.fidilaundry.app.ui.profile.model.ProfileMenu
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CompleteFragment : Fragment() {

    lateinit var loadingDialog: LoadingDialog
    lateinit var paperPrefs: PaperPrefs
//    private var adapter: HistoryAdapter? = null

    val viewModel by sharedViewModel<MasterViewModel>()

    private var _binding: FragmentComplaintCompleteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComplaintCompleteBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner = this@CompleteFragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()

        var adapter = ComplaintListAdapter(context)
        binding.rv.layoutManager =
            ScrollingLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false, 5000)
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(ListDivideritemDecoration(requireContext()))
        adapter.updateList(appList)

    }

    private val appList: List<String>
        get() {
            val appList: MutableList<String> = java.util.ArrayList()
            appList.add("")
            appList.add("")
            appList.add("")
            return appList
        }

    companion object {
        fun newInstance(title: String): Fragment {
            val fragment = CompleteFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}