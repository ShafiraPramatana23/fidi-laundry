package com.fidilaundry.app.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.basearch.viewmodel.ProfileViewModel
import com.fidilaundry.app.databinding.FragmentHomeBinding
import com.fidilaundry.app.databinding.FragmentProfileBinding
import com.fidilaundry.app.ui.auth.LoginActivity
import com.fidilaundry.app.ui.base.BaseFragment
import com.fidilaundry.app.ui.complaint.UserComplaintActivity
import com.fidilaundry.app.ui.home.master.MasterActivity
import com.fidilaundry.app.ui.home.order.UserOrderActivity
import com.fidilaundry.app.ui.profile.adapter.ProfileMenuAdapter
import com.fidilaundry.app.ui.profile.interfaces.IProfile
import com.fidilaundry.app.ui.profile.model.ProfileMenu
import com.fidilaundry.app.ui.scanner.ScannerActivity
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.ScrollingLinearLayoutManager
import com.fidilaundry.app.util.setSafeOnClickListener
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.fragment_home_admin.view.*
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.ArrayList

class ProfileFragment : BaseFragment(), IProfile {
    lateinit var loadingDialog: LoadingDialog

//    private var adapter: NotifListAdapter? = null

    private val viewModel: ProfileViewModel by lazy {
        getViewModel(ProfileViewModel::class)
    }

    private lateinit var vie: View
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner = this@ProfileFragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = paperPref.getDataProfile()
        binding.tvName.text = data?.name
        binding.tvEmail.text = data?.email
        binding.tvPhone.text = data?.phone

        val menuAdapter = ProfileMenuAdapter(context, this)
        binding.rvMenu.adapter = menuAdapter
        binding.rvMenu.layoutManager =
            ScrollingLinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false,
                5000
            )
        menuAdapter?.updateList(appList)

    }

    private val appList: List<ProfileMenu>
        get() {
            val appList: MutableList<ProfileMenu> = ArrayList()
            appList.add(ProfileMenu(1, "Ubah Profile", ""))
            appList.add(ProfileMenu(2, "Ubah Password", ""))
            appList.add(ProfileMenu(3, "Pengaduan Saya", ""))
            appList.add(ProfileMenu(4, "Logout", ""))
            return appList
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(title: String): Fragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }}

    override fun onLogout() {
        paperPref.deleteAllData()
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra("finish", true)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
        startActivity(intent)
        (context as Activity).finish()
    }
}