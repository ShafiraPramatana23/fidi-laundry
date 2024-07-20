package com.fidilaundry.app.ui.notif

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.basearch.viewmodel.NotifViewModel
import com.fidilaundry.app.databinding.FragmentNotifBinding
import com.fidilaundry.app.model.response.NotifResponse
import com.fidilaundry.app.model.response.OrderListResponse
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.ui.base.BaseFragment
import com.fidilaundry.app.ui.home.order.AdminOrderMapsActivity
import com.fidilaundry.app.ui.home.order.interfaces.IFOrder
import com.fidilaundry.app.ui.notif.adapter.NotifAdapter
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.dialog.DialogConfirmOrder
import com.fidilaundry.app.util.fdialog.ErrorMessage
import org.koin.androidx.viewmodel.ext.android.getViewModel

class NotifFragment : BaseFragment(), IFOrder {
    lateinit var loadingDialog: LoadingDialog

    private var profileData: ProfileResponse.Results? = null
    private var adapter: NotifAdapter? = null
    private var dtOrder: OrderListResponse.Result? = null

    private val viewModel: NotifViewModel by lazy {
        getViewModel(NotifViewModel::class)
    }

    private var _binding: FragmentNotifBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotifBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner = this@NotifFragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        initViewModel()

        profileData = paperPref.getDataProfile()

        adapter = NotifAdapter(context, this)
        binding.rvMenu.adapter = adapter
        binding.rvMenu.layoutManager =
            ScrollingLinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false,
                5000
            )
        binding.rvMenu.addItemDecoration(ListDivideritemDecoration(requireContext()))
    }

    private fun initViewModel() {
        viewModel.notifResponse.observe(this, Observer {
            handleWhenNotifSuccess(it)
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

    private fun handleWhenNotifSuccess(it: NotifResponse?) {
        if (it?.results?.size != 0) {
            adapter?.updateList(it?.results!!)
            binding.llEmpty.visibility = View.GONE
        } else {
            binding.llEmpty.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(title: String): Fragment {
            val fragment = NotifFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }}

    override fun onResume() {
        super.onResume()
        viewModel.getNotif(profileData?.id!!)
    }

    override fun onItemClick(data: OrderListResponse.Result) {
        dtOrder = data
        val myRoundedBottomSheet = DialogConfirmOrder(data, this)
        myRoundedBottomSheet.show(childFragmentManager, myRoundedBottomSheet.tag)
    }

    override fun onDialogDismiss() {
        val intent = Intent(context, AdminOrderMapsActivity::class.java)
        intent.putExtra("data", dtOrder)
        startActivity(intent)
    }
}