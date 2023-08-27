package com.fidilaundry.app.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.basearch.viewmodel.OrderViewModel
import com.fidilaundry.app.basearch.viewmodel.SteppingViewModel
import com.fidilaundry.app.databinding.DialogAddDataBinding
import com.fidilaundry.app.databinding.DialogConfirmOrderBinding
import com.fidilaundry.app.databinding.DialogServiceBinding
import com.fidilaundry.app.databinding.DialogUpdateSteppingBinding
import com.fidilaundry.app.model.request.UpdateOrderRequest
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.ui.base.BaseDialogFragment
import com.fidilaundry.app.ui.home.order.adapter.ServiceCategoryAdapter
import com.fidilaundry.app.ui.home.order.interfaces.IFItemClick
import com.fidilaundry.app.ui.home.order.interfaces.IFOrder
import com.fidilaundry.app.util.DateTimeFormater
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.StatusHelper
import com.fidilaundry.app.util.fdialog.ErrorMessage
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DialogUpdateStepping(
    private var data: OrderDetailResponse.Result,
    private var inf: IFOrder
) : BaseDialogFragment() {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    val viewModel by sharedViewModel<SteppingViewModel>()
    private var _binding: DialogUpdateSteppingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogUpdateSteppingBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner =  this@DialogUpdateStepping
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        paperPrefs = PaperPrefs(requireActivity())

        initViewModel()
        viewModel.stepping.value = ""

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnUpdate.setSafeOnClickListener {
            viewModel.updateOrderStatus(UpdateOrderStatusRequest(
                data.code!!, "pengerjaan", viewModel.stepping.value
            ))
        }

        binding.btnDone.setSafeOnClickListener {
            // from cust -> selesai-pengerjaan
            // from admin -> selesai
            viewModel.updateOrderStatus(UpdateOrderStatusRequest(
                data.code!!, "selesai-pengerjaan", viewModel.stepping.value
            ))
        }
//        binding.btnConfirm.setSafeOnClickListener {
//            viewModel.updateOrderStatus(UpdateOrderStatusRequest(
//                data.code!!, StatusHelper.setStatus("pending")
//            ))
//        }
    }

    private fun initViewModel() {
        viewModel.updateOrderStatusResponse.observe(this, Observer {
            handleWhenUpdateSuccess(it)
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

    private fun handleWhenUpdateSuccess(it: UpdateStatusResponse?) {
        inf.onDialogDismiss()
        dismiss()
        // redirect to maps
    }
}