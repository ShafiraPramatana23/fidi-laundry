package com.fidilaundry.app.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.*
import com.fidilaundry.app.databinding.DialogSteppingBinding
import com.fidilaundry.app.ui.base.BaseDialogFragment
import com.fidilaundry.app.ui.home.order.adapter.SteppingAdapter
import com.fidilaundry.app.ui.home.order.interfaces.IFItemClick
import com.fidilaundry.app.ui.home.report.adapter.StatusFilterAdapter
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DialogStepping(
    private var type: Int,
    private var items: MutableList<String>,
    private var inf: IFItemClick
) : BaseDialogFragment(), IFItemClick {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    val viewModel by sharedViewModel<SteppingViewModel>()
    private var _binding: DialogSteppingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSteppingBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner =  this@DialogStepping
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        paperPrefs = PaperPrefs(requireActivity())

        var adapter = SteppingAdapter(activity, this)
        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.adapter = adapter
        adapter.updateList(items, viewModel.stepping.value)

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onItemClick() {

    }

    override fun onItemSelected(value: String?, id: String) {
    }

    override fun onItemSelected(title: String?, value: String, id: Int) {
        viewModel.stepping.value = value
//        viewModel.statusTitle.value = title.toString()
        inf.onItemSelected(title, value, id)
        dismiss()
    }
}