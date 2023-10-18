package com.fidilaundry.app.util.dialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HistoryViewModel
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.basearch.viewmodel.ScannerViewModel
import com.fidilaundry.app.databinding.DialogAddDataBinding
import com.fidilaundry.app.databinding.DialogFilterRepotBinding
import com.fidilaundry.app.databinding.DialogOrderAdminBinding
import com.fidilaundry.app.ui.base.BaseDialogFragment
import com.fidilaundry.app.ui.home.master.model.DropdownItem
import com.fidilaundry.app.ui.home.report.model.Status
import com.fidilaundry.app.ui.home.order.interfaces.IFItemClick
import com.fidilaundry.app.ui.scanner.interfaces.IFClick
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DialogFilterReport(private var inf: IFClick) : BaseDialogFragment(), IFItemClick {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    private var isStart: Boolean = false
    private var dateStart: Date? = null
    private var dateEnd: Date? = null
    private var isEndDate = false
    private var isStartDate = false
    private var isChange = false
    private var selectedDate = ""
    private var dateFilter: MutableList<String> = ArrayList()

    val viewModel by sharedViewModel<HistoryViewModel>()
    private var _binding: DialogFilterRepotBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFilterRepotBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner =  this@DialogFilterReport
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        paperPrefs = PaperPrefs(requireActivity())

        initDatePicker()

        binding.etType.inputType = InputType.TYPE_NULL
        binding.etYear.inputType = InputType.TYPE_NULL
        binding.etStartDate.inputType = InputType.TYPE_NULL
        binding.etEndDate.inputType = InputType.TYPE_NULL

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnSubmit.setSafeOnClickListener {
            dismiss()
            inf.onSubmitClick()
        }

        binding.etType.setSafeOnClickListener {
            val myRoundedBottomSheet = DialogService(1,  serviceList, this)
            myRoundedBottomSheet.show(childFragmentManager, myRoundedBottomSheet.tag)
        }

        binding.etStatus.setSafeOnClickListener {
            val myRoundedBottomSheet = DialogStatus(1,  statusList, this)
            myRoundedBottomSheet.show(childFragmentManager, myRoundedBottomSheet.tag)
        }
    }

    private fun initDatePicker() {
        var cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val formatDate = "dd MMM yyyy" // mention the format you need
                val sdf = SimpleDateFormat(formatDate, Locale.US)

                if (isStart) {
                    dateStart = cal.time
                    dateEnd = Calendar.getInstance().time
                    viewModel.startDate.value = sdf.format(cal.time)
                    viewModel.endDate.value = getDateNow()
                    binding.etStartDate.setText(viewModel.startDate.value)
                    binding.etEndDate.setText(viewModel.endDate.value)
                } else if (isEndDate && viewModel.startDate.value.isEmpty()) {
                    dateStart = cal.time
                    dateEnd = cal.time
                    viewModel.startDate.value = sdf.format(cal.time)
                    viewModel.endDate.value = sdf.format(cal.time)
                    binding.etStartDate.setText(viewModel.startDate.value)
                    binding.etEndDate.setText(viewModel.endDate.value)
                } else {
                    dateEnd = cal.time
                    viewModel.endDate.value = sdf.format(cal.time)
                    binding.etEndDate.setText(viewModel.endDate.value)
                }
            }

        binding.etStartDate.setSafeOnClickListener {
            isStart = true
            isStartDate = false

            var dpd = if (dateStart != null) {
                val cn = Calendar.getInstance()
                cn.time = dateStart

                DatePickerDialog(
                    requireContext(), dateSetListener,
                    cn.get(Calendar.YEAR),
                    cn.get(Calendar.MONTH),
                    cn.get(Calendar.DAY_OF_MONTH)
                )
            } else {
                DatePickerDialog(
                    requireContext(), dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )
            }

            /*if (dateEnd != null) {
                dpd.datePicker.maxDate = dateEnd?.time!!
            }*/
            dpd.datePicker.maxDate = Calendar.getInstance().timeInMillis

            dpd.show()

        }

        binding.etEndDate.setSafeOnClickListener {
            isStart = false
            isEndDate = true

            var dpd = DatePickerDialog(
                requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )

            if (dateStart != null) {
                dpd.datePicker.minDate = dateStart?.time!!
            }
            dpd.datePicker.maxDate = Calendar.getInstance().timeInMillis
            dpd.show()
        }
    }

    private fun getDateNow(): String {
        val c = Calendar.getInstance()
        val formatDate = "dd MMM yyyy" // mention the format you need
        val sdf = SimpleDateFormat(formatDate, Locale.US)
        return sdf.format(c.time)
    }

    private fun getYearly() {
        dateFilter.clear()

        val df1: DateFormat = SimpleDateFormat("dd MMM yyyy")
        val dateFrom: Date = df1.parse("01 Jan 2022")
        val dateTo: Date = df1.parse(getDateNow())
        val locale = Locale.US

        val df2: DateFormat = SimpleDateFormat("yyyy")
        val years: MutableList<String> = getListYears(dateFrom, dateTo, locale, df2)!!
        dateFilter = years.reversed().toMutableList()

        if (viewModel.startDate.value.isEmpty()) {
            selectedDate = dateFilter[0]
            viewModel.year.value = selectedDate
            binding.etYear.setText(selectedDate)
            isChange = false
        } else {
            if (isChange) {
                selectedDate = dateFilter[0]
                isChange = !isChange
            }
            binding.etYear.setText(selectedDate)
//            setEndDateValue(Calendar.DATE, 364, selectedDate)
        }
    }

    fun getListYears(
        dateFrom: Date?, dateTo: Date,
        locale: Locale?, df: DateFormat
    ): MutableList<String>? {
        val calendar = Calendar.getInstance(locale)
        calendar.time = dateFrom
        val year: MutableList<String> = ArrayList()
        while (calendar.time.time <= dateTo.time) {
            year.add(df.format(calendar.time))
            calendar.add(Calendar.YEAR, 1)
        }
        return year
    }

    private val serviceList: List<DropdownItem>
        get() {
            val appList: MutableList<DropdownItem> = ArrayList()
            appList.add(DropdownItem(1, "Custom"))
            appList.add(DropdownItem(2, "Tahunan"))
            return appList
        }

    private val statusList: List<Status>
        get() {
            val appList: MutableList<Status> = ArrayList()
            appList.add(Status(1, "Pending", "pending"))
            appList.add(Status(1, "Dijemput", "dijemput"))
            appList.add(Status(1, "Pengecekan Baju", "cek item"))
            appList.add(Status(1, "Pengerjaan", "pengerjaan"))
            appList.add(Status(1, "Pengerjaan Selesai", "selesai-pengerjaan"))
            appList.add(Status(1, "Diantar", "antar"))
            appList.add(Status(1, "Selesai", "selesai"))
            appList.add(Status(1, "Menunggu Pembayaran", "menunggu-pembayaran"))
            appList.add(Status(1, "Pembayaran Selesai", "pembayaran-sukses"))
            appList.add(Status(1, "Komplain", "komplain"))
            appList.add(Status(1, "Komplain Terselesaikan", "solve"))
//            appList.add(Status(1, "", ""))
            return appList
        }

    override fun onItemClick() {

    }

    override fun onItemSelected(value: String?, id: String) {
        viewModel.typeFilter.value = value!!

        if (value == "Tahunan") {
            getYearly()
            binding.llYear.visibility = View.VISIBLE
            binding.llDate.visibility = View.GONE
        } else {
            binding.llYear.visibility = View.GONE
            binding.llDate.visibility = View.VISIBLE
        }
    }

    override fun onItemSelected(title: String?, value: String, id: Int) {
        viewModel.status.value = value
        viewModel.statusTitle.value = title.toString()
    }
}