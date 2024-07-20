package com.fidilaundry.app.util.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.PaymentViewModel
import com.fidilaundry.app.databinding.DialogPaymentMethodBinding
import com.fidilaundry.app.model.request.CreatePaymentRequest
import com.fidilaundry.app.model.response.CreatePaymentResponse
import com.fidilaundry.app.model.response.OrderDetailResponse
import com.fidilaundry.app.model.response.UpdateStatusResponse
import com.fidilaundry.app.ui.base.BaseDialogFragment
import com.fidilaundry.app.ui.home.order.PaymentActivity
import com.fidilaundry.app.ui.home.order.interfaces.IFPayment
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.fdialog.ErrorMessage
import com.fidilaundry.app.util.fdialog.FGCallback
import com.fidilaundry.app.util.fdialog.SuccessMessage
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DialogPaymentMethod(
    private var dtDetail: OrderDetailResponse.Result?,
    private var inf: IFPayment
) : BaseDialogFragment() {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    val viewModel by sharedViewModel<PaymentViewModel>()
    private var _binding: DialogPaymentMethodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPaymentMethodBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner =  this@DialogPaymentMethod
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        paperPrefs = PaperPrefs(requireActivity())

        initViewModel()

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnSubmit.setSafeOnClickListener {
            viewModel.createPayment(CreatePaymentRequest(
                dtDetail?.id!!, viewModel.paymentMethod.value.toInt()
            ))

//            when (viewModel.paymentMethod.value) {
//                "1" -> {
//                    viewModel.createPayment(CreatePaymentRequest(
//                        dtDetail?.id!!, viewModel.paymentMethod.value.toInt()
//                    ))
//                }
//                "2" -> {
//
//                }
//            }
        }
    }

    private fun initViewModel() {
        viewModel.updateOrderStatusResponse.observe(this, Observer {
            handleWhenUpdateStatusSuccess(it)
        })

        viewModel.createPaymentResponse.observe(this, Observer {
            handleWhenCreatePaymentSuccess(it)
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

        viewModel.showError.observe(requireActivity(), Observer { showError ->
            ErrorMessage(requireActivity(), "", showError)
        })
    }

    private fun handleWhenCreatePaymentSuccess(it: CreatePaymentResponse?) {
        when (viewModel.paymentMethod.value) {
            "1" -> {
                SuccessMessage(requireActivity(), "Pembayaran Tunai (Cash)", "Silahkan datang ke laundry untuk mengambil pesanan Anda", object : FGCallback {
                    override fun onCallback() {
                        dismiss()
                        inf.onDialogPayment()
                    }
                })
            }
            "2" -> {
                dismiss()
                val intent = Intent(requireActivity(), PaymentActivity::class.java)
                intent.putExtra("snapToken", it?.results?.snapToken)
                intent.putExtra("paymentId", it?.results?.paymentId)
                intent.putExtra("data", dtDetail)
                startActivity(intent)
            }
        }
    }

    private fun handleWhenUpdateStatusSuccess(it: UpdateStatusResponse?) {
        when (viewModel.paymentMethod.value) {
            "1" -> {
            }
            "2" -> {

            }
        }
    }
}