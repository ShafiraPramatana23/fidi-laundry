package com.fidilaundry.app.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.databinding.DialogAddDataBinding
import com.fidilaundry.app.databinding.DialogUpdateDataBinding
import com.fidilaundry.app.ui.base.BaseDialogFragment
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.fdialog.ConfirmMessage
import com.fidilaundry.app.util.fdialog.FGCallback
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DialogUpdateData(
    private var type: Int
) : BaseDialogFragment() {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    val viewModel by sharedViewModel<HomeViewModel>()
    private var _binding: DialogUpdateDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogUpdateDataBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner =  this@DialogUpdateData
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        paperPrefs = PaperPrefs(requireActivity())

        when (type) {
            1 -> binding.tvTitle.text = "Ubah Data Barang"
            2 -> binding.tvTitle.text = "Ubah Data Kategori"
            3 -> binding.tvTitle.text = "Ubah Data Jenis Cuci"
            4 -> binding.tvTitle.text = "Ubah Data Layanan"
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnUpdate.setSafeOnClickListener {

        }

        binding.btnDelete.setSafeOnClickListener {
            confirmDelete()
        }
    }

    private fun confirmDelete() {
        ConfirmMessage(
            requireActivity(),
            "Apakah Anda yakin menghapus data ini?",
            "", "",
            "Hapus", "Batal",
            object : FGCallback {
                override fun onCallback() {

                }
            }
        )
    }
}