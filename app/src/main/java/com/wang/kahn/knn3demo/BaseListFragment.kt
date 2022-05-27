package com.wang.kahn.knn3demo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.example.rocketreserver.apolloClient
import com.wang.kahn.knn3demo.databinding.FragmentListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
abstract class BaseListFragment(protected val query: LiveData<String>) : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query.observe(this) { refreshWithScope() }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshWithScope()
        binding.refresh.setOnRefreshListener {
            refreshWithScope()
        }
    }

    private fun refreshWithScope() {
        binding.refresh.isRefreshing = true
        lifecycleScope.launchWhenResumed {
            refreshData()
            binding.refresh.isRefreshing = false
        }
    }

    abstract suspend fun refreshData();

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}