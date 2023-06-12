package com.fidilaundry.app.ui.home.master

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.databinding.ActivityMasterBinding
import com.fidilaundry.app.ui.base.BaseActivity
import com.fidilaundry.app.ui.home.master.adapter.MasterMenuAdapter
import com.fidilaundry.app.ui.home.master.adapter.PriceListAdapter
import com.fidilaundry.app.ui.home.master.model.MasterMenu
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.ArrayList

class MasterActivity : BaseActivity() {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    private val binding: ActivityMasterBinding by binding(R.layout.activity_master)

    private val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MasterActivity
            this.vm = viewModel
        }

        var adapter = MasterMenuAdapter(this)
        binding.rv.layoutManager = GridLayoutManager(this, 2)
        binding.rv.adapter = adapter
        adapter.updateList(appList)

        binding.ivBack.setSafeOnClickListener {
            finish()
        }
    }

    private val appList: List<MasterMenu>
        get() {
            val appList: MutableList<MasterMenu> = ArrayList()
//            appList.add(MasterMenu(2, "Master Kategori"))
            appList.add(MasterMenu(1, "Master Jenis Cuci"))
            appList.add(MasterMenu(2, "Master Layanan"))
            appList.add(MasterMenu(3, "Master Barang"))
//            appList.add(MasterMenu(2, "Master Harga"))
            appList.add(MasterMenu(4, "Master Pelanggan"))
            return appList
        }
}