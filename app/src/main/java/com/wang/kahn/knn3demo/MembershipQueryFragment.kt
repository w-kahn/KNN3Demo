package com.wang.kahn.knn3demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

const val DEFAULT_QUERY_MEM = "if_fans_token_threshold"
class MembershipQueryFragment : NormalQueryFragment() {
    protected override val queryContent: MutableLiveData<String> by lazy {
        MutableLiveData<String>(DEFAULT_QUERY_MEM)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textField.hint = context?.getString(R.string.membership_query_hint)
        binding.textField.editText?.setText(queryContent.value)
    }

    override fun contentListFragment(queryContent: MutableLiveData<String>): Fragment {
        return MembershipListFragment(queryContent)
    }
}