package com.wang.kahn.knn3demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.wang.kahn.knn3demo.databinding.FragmentDetailBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    val args: DetailFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(view.context).load(args.nftUrl).into(binding.nftDetailImage)
        binding.nftSymbolText.text = args.nftSymbol
        binding.nftContractAddress.text = args.nftContract
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}