package com.wang.kahn.knn3demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Query

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NFTListFragment(query: LiveData<String>) : BaseListFragment<NFTQuery.Data>(query) {

    override fun getQuery(): Query<NFTQuery.Data> {
        return NFTQuery(Optional.presentIfNotNull(query.value?.lowercase()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.layoutManager =
            GridLayoutManager(requireContext(), 2)
    }

    override fun notifyData(data: NFTQuery.Data) {
        if (data.addrs.isEmpty() || data.addrs[0].holdnfts.isEmpty()) {
            model.setEmpty(true)
            return
        }
        model.setEmpty(false)
        data.addrs[0].holdnfts
            .filter { !it.imageUrl.isNullOrBlank() }
            .let {
            val adapter = NFTListAdapter(it)
            binding.list.adapter = adapter
            adapter.onItemClickListener = { nft ->
                findNavController().navigate(
                    NFTListFragmentDirections.openNftDetail(
                        nft.imageUrl,
                        nft.symbol,
                        nft.contract
                    )
                )
            }
        }
    }


}