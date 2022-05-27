package com.wang.kahn.knn3demo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.exception.ApolloException
import com.example.rocketreserver.apolloClient
import com.wang.kahn.knn3demo.databinding.FragmentListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
abstract class BaseListFragment<T : Query.Data>(protected val query: LiveData<String>) : Fragment() {

    private var _binding: FragmentListBinding? = null

    val model: BaseListViewModel<T> by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query.observe(this) { refreshData() }
        model.empty.observe(this) { isEmpty ->
            if (isEmpty == true){
                binding.emptyHint.visibility = View.VISIBLE
                binding.list.visibility = View.GONE
            }
            else{
                binding.emptyHint.visibility = View.GONE
                binding.list.visibility = View.VISIBLE
            }
        }
        model.loading.observe(this) {
            binding.refresh.isRefreshing = it
        }

        model.data.observe(this) {
            notifyData(it)
        }
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
        refreshData()
        binding.refresh.setOnRefreshListener {
            refreshData()
        }
    }

    private fun refreshData() {
        model.loadData(getQuery())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getQuery(): Query<T>

    abstract fun notifyData(data: T)
}