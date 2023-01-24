package com.fidilaundry.app.ui.history

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.ui.base.BaseFragment
import com.fidilaundry.app.util.LoadingDialog
import org.koin.androidx.viewmodel.ext.android.getViewModel
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.fidilaundry.app.basearch.viewmodel.HomeViewModel
import com.fidilaundry.app.databinding.FragmentHistoryBinding
import com.fidilaundry.app.databinding.FragmentHomeBinding
import com.fidilaundry.app.ui.home.order.UserOrderActivity
import com.fidilaundry.app.util.FontTextView
import com.fidilaundry.app.util.setSafeOnClickListener

class HistoryFragment : BaseFragment() {
    lateinit var loadingDialog: LoadingDialog
    lateinit var paperPrefs: PaperPrefs

    private var pos = 0

    private var fragmentPagerAdapter: FragmentStateAdapter? = null

    private val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
    }

    private lateinit var vie: View
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner = this@HistoryFragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentPagerAdapter = TabPagerAdapter(requireActivity())
        binding.viewPager.adapter = fragmentPagerAdapter

        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pos = position
                when (position) {
                    0 -> inprogressActive()
                    1 -> completeActive()
                }
            }
        })

        binding.llInprogress.setOnClickListener {
            binding.viewPager.currentItem = 0
            inprogressActive()
        }

        binding.llComplete.setOnClickListener {
            binding.viewPager.currentItem = 1
            completeActive()
        }
    }

    private fun inprogressActive() {
        activeLayout(binding.lineInprogress, binding.tvInprogress)
        inactiveLayout(binding.lineComplete, binding.tvComplete)
    }

    private fun completeActive() {
        inactiveLayout(binding.lineInprogress, binding.tvInprogress)
        activeLayout(binding.lineComplete, binding.tvComplete)
    }

    private fun inactiveLayout(rl: RelativeLayout, tv: FontTextView) {
        rl.background = null
        tv.setTextColor(
            ResourcesCompat.getColor(resources, R.color.grey_text, null)
        )
    }

    private fun activeLayout(rl: RelativeLayout, tv: FontTextView) {
        rl.background = ContextCompat.getDrawable(requireActivity(), R.drawable.bg_tab_line)
        tv.setTextColor(ResourcesCompat.getColor(resources, R.color.blackHistory, null))
    }

    internal inner class TabPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        private val inprogress = HistoryInprogressFragment.newInstance("")
        private val complete = HistoryCompleteFragment.newInstance("")

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> inprogress
                else -> complete
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(title: String): Fragment {
            val fragment = HistoryFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }}
}