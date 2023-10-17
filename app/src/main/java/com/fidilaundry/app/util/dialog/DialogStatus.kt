package com.fidilaundry.app.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HistoryViewModel
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.basearch.viewmodel.OrderViewModel
import com.fidilaundry.app.basearch.viewmodel.ScannerViewModel
import com.fidilaundry.app.databinding.DialogAddDataBinding
import com.fidilaundry.app.databinding.DialogServiceBinding
import com.fidilaundry.app.databinding.DialogStatusBinding
import com.fidilaundry.app.ui.base.BaseDialogFragment
import com.fidilaundry.app.ui.home.master.model.DropdownItem
import com.fidilaundry.app.ui.home.report.model.Status
import com.fidilaundry.app.ui.home.order.adapter.ServiceCategoryAdapter
import com.fidilaundry.app.ui.home.order.interfaces.IFItemClick
import com.fidilaundry.app.ui.home.report.adapter.StatusFilterAdapter
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DialogStatus(
    private var type: Int,
    private var items: List<Status>,
    private var inf: IFItemClick
) : BaseDialogFragment(), IFItemClick {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    val viewModel by sharedViewModel<HistoryViewModel>()
    private var _binding: DialogStatusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogStatusBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner =  this@DialogStatus
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        paperPrefs = PaperPrefs(requireActivity())

        var adapter = StatusFilterAdapter(activity, this)
        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.adapter = adapter
        adapter.updateList(items, viewModel.status.value)

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onItemClick() {

    }

    override fun onItemSelected(value: String?, id: String) {
    }

    override fun onItemSelected(title: String?, value: String, id: Int) {
        viewModel.status.value = value
        viewModel.statusTitle.value = title.toString()
        inf.onItemSelected(title, value, id)
        dismiss()
    }
}