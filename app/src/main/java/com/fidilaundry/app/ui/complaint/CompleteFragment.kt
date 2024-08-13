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
import com.fidilaundry.app.basearch.viewmodel.ComplaintViewModel
import com.fidilaundry.app.basearch.viewmodel.MasterViewModel
import com.fidilaundry.app.databinding.FragmentComplaintCompleteBinding
import com.fidilaundry.app.databinding.FragmentComplaintInprogressBinding
import com.fidilaundry.app.model.response.ComplaintListResponse
import com.fidilaundry.app.ui.complaint.adapter.ComplaintListAdapter
import com.fidilaundry.app.ui.home.master.adapter.ItemListAdapter
import com.fidilaundry.app.ui.profile.model.ProfileMenu
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.fdialog.ErrorMessage
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CompleteFragment : Fragment() {

    lateinit var loadingDialog: LoadingDialog
    lateinit var paperPrefs: PaperPrefs

    private var adapter: ComplaintListAdapter? = null

    val viewModel by sharedViewModel<ComplaintViewModel>()

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
        paperPrefs = PaperPrefs(requireContext())

        initViewModel()

        adapter = ComplaintListAdapter(context, 2)
        binding.rv.layoutManager =
            ScrollingLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false, 5000)
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(ListDivideritemDecoration(requireContext()))

        viewModel.getComplaintList(2)
    }

    private fun initViewModel() {
        viewModel.complaintCompleteRes.observe(this, Observer {
            handleWhenListSuccess(it)
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

    private fun handleWhenListSuccess(it: ComplaintListResponse?) {
        var profile = paperPrefs.getDataProfile()
        if (it?.results?.size != 0) {
            binding.llEmpty.visibility = View.GONE
            val appList: MutableList<ComplaintListResponse.Result> = ArrayList()
            for (i in 0 until it?.results?.size!!) {
                if (it?.results?.get(i)?.feedbacks?.size!! > 0) {
                    if (profile?.role =="customer" && profile?.id == it.results!!.get(i).userID) {
                        appList.add(it?.results?.get(i)!!)
                    }

                    if (profile?.role !="customer") {
                        appList.add(it?.results?.get(i)!!)
                    }
                }
            }

            if (appList.size > 0) {
                adapter?.updateList(appList)
                binding.llEmpty.visibility = View.GONE
            } else {
                binding.llEmpty.visibility = View.VISIBLE
            }
        } else {
            binding.llEmpty.visibility = View.VISIBLE
        }
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