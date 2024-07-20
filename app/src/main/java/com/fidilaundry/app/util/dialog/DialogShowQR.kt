package com.fidilaundry.app.util.dialog

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.basearch.viewmodel.OrderViewModel
import com.fidilaundry.app.databinding.DialogAddDataBinding
import com.fidilaundry.app.databinding.DialogConfirmOrderBinding
import com.fidilaundry.app.databinding.DialogServiceBinding
import com.fidilaundry.app.databinding.DialogShowQrBinding
import com.fidilaundry.app.ui.base.BaseDialogFragment
import com.fidilaundry.app.ui.home.order.adapter.ServiceCategoryAdapter
import com.fidilaundry.app.ui.home.order.interfaces.IFItemClick
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.setSafeOnClickListener
import com.google.zxing.WriterException
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DialogShowQR : BaseDialogFragment() {

    lateinit var loadingDialog: LoadingDialog

    val viewModel by sharedViewModel<OrderViewModel>()
    private var _binding: DialogShowQrBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogShowQrBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner =  this@DialogShowQR
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()

        var profileData = paperPref.getDataProfile()
        generateQr(profileData?.id.toString(), binding.ivQr)

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun generateQr(inputValue: String, ivQrCode: ImageView) {
        val manager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        val display: Display = manager!!.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width: Int = point.x
        val height: Int = point.y

        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4
        val qrgEncoder = QRGEncoder(inputValue, null, QRGContents.Type.TEXT, dimen)
        try {
            var bitmap = qrgEncoder.getBitmap(0)
            ivQrCode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.v("ERR_GENERATE", e.toString())
        }
    }
}