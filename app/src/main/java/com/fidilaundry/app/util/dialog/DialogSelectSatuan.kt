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
import com.fidilaundry.app.databinding.DialogSelectSatuanBinding
import com.fidilaundry.app.model.request.ItemService
import com.fidilaundry.app.model.request.ItemServiceTitle
import com.fidilaundry.app.model.response.ItemListResponse
import com.fidilaundry.app.model.response.ResultData
import com.fidilaundry.app.ui.base.BaseDialogFragment
import com.fidilaundry.app.ui.home.master.adapter.DropdownAdapter
import com.fidilaundry.app.ui.home.master.model.DropdownItem
import com.fidilaundry.app.ui.home.order.adapter.SatuanAdapter
import com.fidilaundry.app.ui.home.order.interfaces.IFItemClick
import com.fidilaundry.app.ui.home.order.interfaces.IFSatuan
import com.fidilaundry.app.ui.home.order.model.SelectedSatuanItem
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.ArrayList

class DialogSelectSatuan(
    private var list: List<ItemListResponse.Result>,
    private var selectedList: List<ItemService>,
    private var inf: IFSatuan
) : BaseDialogFragment(), IFSatuan {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    private var adapter: SatuanAdapter? = null

    private var selectedItem: MutableList<ItemService> = ArrayList()
    private var selectedItemDisplay: MutableList<ItemServiceTitle> = ArrayList()

    val viewModel by sharedViewModel<OrderViewModel>()
    private var _binding: DialogSelectSatuanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSelectSatuanBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner =  this@DialogSelectSatuan
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        paperPrefs = PaperPrefs(requireActivity())

        adapter = SatuanAdapter(activity, this)
        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.adapter = adapter
        adapter?.updateList(list)

        if (selectedList.isNotEmpty()) {
            selectedItem = selectedList.toMutableList()
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnSubmit.setSafeOnClickListener {
            dismiss()
            inf.onDialogDismiss(selectedItem, selectedItemDisplay)
        }
    }

    override fun onItemSelected(value: SelectedSatuanItem?) {
        var idx = selectedItem.indexOfFirst { it.item_id == value?.id }
        var idxD = selectedItemDisplay.indexOfFirst { it.item_id == value?.id }
        var totalAmount = value?.price!! * value?.qty!!

        if (idx >= 0) {
            val updateQty = selectedItem[idx].apply {
                qty = value?.qty!!
                amount = totalAmount
            }
            selectedItem[idx] = updateQty

            val updateQtyD = selectedItemDisplay[idxD].apply {
                qty = value?.qty!!
                amount = totalAmount
            }
            selectedItemDisplay[idx] = updateQtyD
        } else {
            selectedItem.add(ItemService(value?.id!!, totalAmount, value.qty))
            selectedItemDisplay.add(ItemServiceTitle(value?.id!!, value.title, totalAmount, value.qty))
        }
    }

    override fun onDialogDismiss(value: MutableList<ItemService>?, display: MutableList<ItemServiceTitle>?) {}
}