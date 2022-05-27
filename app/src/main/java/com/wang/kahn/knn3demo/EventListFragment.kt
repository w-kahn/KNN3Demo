package com.wang.kahn.knn3demo

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.example.rocketreserver.apolloClient

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
const val TEST_EVENT_ADDRESS_TO_QUERY = "0x896002e29fe4cda28a3ae139b0bf7bac26b33a8c"

class EventListFragment : BaseListFragment() {

    override suspend fun refreshData() {
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