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

const val ADDRESS_TO_QUERY = "0xae89ad222e67205e8d947f131fdc9fa139828745"
/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NFTListFragment : Fragment() {

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
                apolloClient(requireContext()).query(NFTQuery(Optional.presentIfNotNull(
                    ADDRESS_TO_QUERY.lowercase()))).execute()
            } catch (e: ApolloException) {
                Log.e("NFTList", "request fail", e)
                null
            }
            response?.data?.addrs?.get(0)?.holdnfts?.let {
                if (!response.hasErrors()) {
                    binding.nftList.layoutManager = LinearLayoutManager(requireContext())
                    val adapter =  NFTListAdapter(it)
                    binding.nftList.adapter = adapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}