package com.fidilaundry.app.ui.home.master

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.databinding.ActivityMasterBinding
import com.fidilaundry.app.databinding.ActivityPriceListBinding
import com.fidilaundry.app.databinding.ActivityUserListBinding
import com.fidilaundry.app.ui.base.BaseActivity
import com.fidilaundry.app.ui.home.master.adapter.PriceListAdapter
import com.fidilaundry.app.ui.home.master.adapter.UserListAdapter
import com.fidilaundry.app.util.ListDivideritemDecoration
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.fdialog.ConfirmMessage
import com.fidilaundry.app.util.fdialog.FGCallback
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.ArrayList

class PriceListActivity : BaseActivity() {

    lateinit var paperPrefs: PaperPrefs
    lateinit var loadingDialog: LoadingDialog

    private val binding: ActivityPriceListBinding by binding(R.layout.activity_price_list)

    private val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@PriceListActivity
            this.vm = viewModel
        }

        var adapter = PriceListAdapter(this)
        binding.rv.layoutManager =
            ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, 5000)
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(ListDivideritemDecoration(this))
        adapter.updateList(appList)

        binding.ivBack.setSafeOnClickListener {
            finish()
        }

        binding.extendedFab.setSafeOnClickListener {
            val intent = Intent(this, AddPriceActivity::class.java)
            startActivity(intent)
        }
    }

    private val appList: List<String>
        get() {
            val appList: MutableList<String> = ArrayList()
            appList.add("Shafira")
            appList.add("Pramatana")
            appList.add("Rachmadanti")
            return appList
        }
}