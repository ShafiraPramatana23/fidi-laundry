package com.fidilaundry.app.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.MasterViewModel
import com.fidilaundry.app.basearch.viewmodel.OrderViewModel
import com.fidilaundry.app.databinding.DialogDropdownBinding
import com.fidilaundry.app.ui.base.BaseDialogFragment
import com.fidilaundry.app.ui.home.master.adapter.DropdownAdapter
import com.fidilaundry.app.ui.home.master.model.DropdownItem
import com.fidilaundry.app.ui.home.order.interfaces.IFItemClick
import com.fidilaundry.app.util.LoadingDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DialogDropdown(
    private var type: Int,
    private var list: List<DropdownItem>,
    private var inf: IFItemClick
) : BaseDialogFragment(), IFItemClick {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    val viewModel by sharedViewModel<MasterViewModel>()
    private var _binding: DialogDropdownBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogDropdownBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner =  this@DialogDropdown
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        paperPrefs = PaperPrefs(requireActivity())

        var adapter = DropdownAdapter(activity, this)
        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.adapter = adapter

        var selected = ""
        when (type) {
            1 -> selected = viewModel.ctgValue.value
            2 -> selected = viewModel.itemsValue.value
            3 -> selected = viewModel.serviceValue.value
        }
        adapter.updateList(list, selected)

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onItemSelected(value: String?, id: String) {
        println("huhuy: "+ value)
        inf.onItemSelected(value, id)
        dismiss()
    }

    override fun onItemSelected(value: String?, id: String, type: Int) {
    }
}