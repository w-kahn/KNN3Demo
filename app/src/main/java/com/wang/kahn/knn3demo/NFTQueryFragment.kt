package com.wang.kahn.knn3demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

class NFTQueryFragment : NormalQueryFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textField.hint = context?.getString(R.string.nft_query_hint)
    }

    override fun contentListFragment(queryContent: MutableLiveData<String>): Fragment {
        return NFTHoldersFragment(queryContent)
    }
}