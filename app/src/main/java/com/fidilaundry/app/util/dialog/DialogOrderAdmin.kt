package com.fidilaundry.app.util.dialog

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.basearch.viewmodel.ScannerViewModel
import com.fidilaundry.app.databinding.DialogAddDataBinding
import com.fidilaundry.app.databinding.DialogOrderAdminBinding
import com.fidilaundry.app.ui.base.BaseDialogFragment
import com.fidilaundry.app.ui.home.master.model.DropdownItem
import com.fidilaundry.app.ui.home.order.interfaces.IFItemClick
import com.fidilaundry.app.ui.scanner.interfaces.IFClick
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.ArrayList

class DialogOrderAdmin(private var inf: IFClick) : BaseDialogFragment(), IFItemClick {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    val viewModel by sharedViewModel<ScannerViewModel>()
    private var _binding: DialogOrderAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogOrderAdminBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner =  this@DialogOrderAdmin
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        paperPrefs = PaperPrefs(requireActivity())

        binding.etService.inputType = InputType.TYPE_NULL

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnSubmit.setSafeOnClickListener {
            dismiss()
            inf.onSubmitClick()
        }

        binding.etService.setSafeOnClickListener {
            val myRoundedBottomSheet = DialogService(1,  serviceList, this)
            myRoundedBottomSheet.show(childFragmentManager, myRoundedBottomSheet.tag)
        }
    }

    private val serviceList: List<DropdownItem>
        get() {
            val appList: MutableList<DropdownItem> = ArrayList()
            appList.add(DropdownItem(1, "Cuci Kering"))
            appList.add(DropdownItem(2, "Cuci Basah"))
            appList.add(DropdownItem(3, "Cuci Setrika"))
            appList.add(DropdownItem(4, "Setrika"))
            return appList
        }

    override fun onItemClick() {

    }

    override fun onItemSelected(value: String?, id: String) {
        viewModel.serviceId.value = id
        viewModel.service.value = value!!
    }

    override fun onItemSelected(value: String?, id: String, type: Int) {
    }
}