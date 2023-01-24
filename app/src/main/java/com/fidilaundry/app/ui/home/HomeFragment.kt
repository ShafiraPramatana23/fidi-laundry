package com.fidilaundry.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.databinding.FragmentHomeBinding
import com.fidilaundry.app.ui.base.BaseFragment
import com.fidilaundry.app.ui.complaint.UserComplaintActivity
import com.fidilaundry.app.ui.home.order.UserOrderActivity
import com.fidilaundry.app.util.LoadingDialog
import com.fidilaundry.app.util.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeFragment : BaseFragment() {
    lateinit var loadingDialog: LoadingDialog
    lateinit var paperPrefs: PaperPrefs
//    private var adapter: NotifListAdapter? = null

    private val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
    }

    private lateinit var vie: View
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner = this@HomeFragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSetrika.setSafeOnClickListener {
            activity?.intent = Intent(activity, UserOrderActivity::class.java)
            startActivity(activity?.intent)
        }

        binding.btnCusek.setSafeOnClickListener {
            activity?.intent = Intent(activity, UserComplaintActivity::class.java)
            startActivity(activity?.intent)
        }

//        val myRoundedBottomSheet = DialogAngsuran()
//        myRoundedBottomSheet.show(supportFragmentManager, myRoundedBottomSheet.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(title: String): Fragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }}
}