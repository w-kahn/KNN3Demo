package com.wang.kahn.knn3demo

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.example.rocketreserver.apolloClient

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NFTListFragment : BaseListFragment() {

    override suspend fun refreshData() {
        val response = try {
            apolloClient(requireContext()).query(NFTQuery(Optional.presentIfNotNull(
                ADDRESS_TO_QUERY.lowercase()))).execute()
        } catch (e: ApolloException) {
            Log.e("NFTList", "request fail", e)
            null
        }
        response?.data?.addrs?.get(0)?.holdnfts?.let {
            if (!response.hasErrors()) {
                binding.list.layoutManager = LinearLayoutManager(requireContext())
                val adapter =  NFTListAdapter(it)
                binding.list.adapter = adapter
                adapter.onItemClickListener = { nft ->
                    findNavController().navigate(
                        NFTListFragmentDirections.openNftDetail(
                            nft.imageUrl,
                            nft.symbol
                        )
                    )
                }
            }
        }
    }
}