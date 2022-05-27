package com.wang.kahn.knn3demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Query

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class SocialListFragment(query: LiveData<String>) : BaseListFragment<SocialQuery.Data>(query) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun getQuery(): Query<SocialQuery.Data> {
        return SocialQuery(Optional.presentIfNotNull(query.value?.lowercase()))
    }

    override fun notifyData(data: SocialQuery.Data) {
        if (data.addrs.isEmpty() || data.addrs[0].addrsFollow.isEmpty()) {
            model.setEmpty()
            return
        }
        binding.list.adapter = SocialListAdapter(data.addrs[0].addrsFollow)
    }
}