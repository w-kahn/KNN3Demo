package com.wang.kahn.knn3demo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
const val TEST_EVENT_ADDRESS_TO_QUERY = "0x896002e29fe4cda28a3ae139b0bf7bac26b33a8c"

class EventListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient(requireContext()).query(POAPEventsQuery(Optional.presentIfNotNull(
                    TEST_EVENT_ADDRESS_TO_QUERY.lowercase()))).execute()
            } catch (e: ApolloException) {
                Log.e("NFTList", "request fail", e)
                null
            }
            response?.data?.addrs?.get(0)?.attendEvents?.let {
                if (!response.hasErrors()) {
                    binding.list.layoutManager = LinearLayoutManager(requireContext())
                    val adapter =  EventListAdapter(it)
                    binding.list.adapter = adapter
                    adapter.onItemClickListener = { follow ->
                        // TODO: to jump address query
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}