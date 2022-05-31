package com.wang.kahn.knn3demo

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.wang.kahn.knn3demo.databinding.FragmentDetailBinding
import java.io.File

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
        binding.downloadButton.setOnClickListener {
            downloadImage(args.nftUrl!!, view.context)
            Toast.makeText(view.context,R.string.start_download,Toast.LENGTH_SHORT).show()
        }

        binding.shareButton.setOnClickListener{
            Glide.with(view.context).download(args.nftUrl).into(object : CustomTarget<File>() {
                override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                    activity?.let { it1 -> shareImage(resource, it1) }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}