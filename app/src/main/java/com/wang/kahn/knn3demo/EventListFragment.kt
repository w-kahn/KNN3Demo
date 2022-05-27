package com.wang.kahn.knn3demo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.exception.ApolloException
import com.example.rocketreserver.apolloClient

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class EventListFragment(query: LiveData<String>) : BaseListFragment<POAPEventsQuery.Data>(query) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun getQuery(): Query<POAPEventsQuery.Data> {
        return POAPEventsQuery(Optional.presentIfNotNull(query.value?.lowercase()))
    }

    override fun notifyData(data: POAPEventsQuery.Data) {
        if (data.addrs.isEmpty() || data.addrs[0].attendEvents.isEmpty()) {
            model.setEmpty()
            return
        }

        binding.list.adapter = EventListAdapter(data.addrs[0].attendEvents)
    }
}