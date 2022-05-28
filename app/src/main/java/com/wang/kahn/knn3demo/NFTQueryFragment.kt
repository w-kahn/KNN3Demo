package com.wang.kahn.knn3demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

const val DEFAULT_NFT_QUERY = "CRYPTOPUNKS"
class NFTQueryFragment : NormalQueryFragment() {
    override val queryContent: MutableLiveData<String> by lazy {
        MutableLiveData<String>(DEFAULT_NFT_QUERY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textField.hint = context?.getString(R.string.nft_query_hint)
        binding.textField.editText?.setText(queryContent.value)
    }

    override fun contentListFragment(queryContent: MutableLiveData<String>): Fragment {
        return NFTHoldersFragment(queryContent)
    }
}