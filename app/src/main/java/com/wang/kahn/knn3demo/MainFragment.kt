package com.wang.kahn.knn3demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.wang.kahn.knn3demo.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val tabs = listOf("NFTs", "Social", "Action", "Status", "Membership")

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewPager: ViewPager2
        get() = binding.pager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = PagerAdapter(requireActivity())
        binding.addressText.text = ADDRESS_TO_QUERY
        TabLayoutMediator(binding.tabLayout, viewPager
        ) { tab, position -> tab.text = tabs[position] }.attach()

    }

    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = tabs.size

        override fun createFragment(position: Int): Fragment = NFTListFragment()
    }

}