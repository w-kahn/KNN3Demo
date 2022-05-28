package com.wang.kahn.knn3demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

class MembershipQueryFragment : NormalQueryFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textField.hint = context?.getString(R.string.membership_query_hint)
    }

    override fun contentListFragment(queryContent: MutableLiveData<String>): Fragment {
        return MembershipListFragment(queryContent)
    }
}