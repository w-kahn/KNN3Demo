package com.wang.kahn.knn3demo

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.wang.kahn.knn3demo.databinding.FragmentNormalQueryBinding

abstract class NormalQueryFragment : Fragment() {

    private var _binding: FragmentNormalQueryBinding? = null

    protected val binding get() = _binding!!

    private val queryContent: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNormalQueryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentFragment = contentListFragment(queryContent)
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, contentFragment).commit()

        binding.textField.editText?.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    goSearch()
                    return true
                }
                return false
            }
        })

    }

    private fun goSearch() {
        queryContent.value = binding.textField.editText?.text?.toString()
    }

    abstract fun contentListFragment(queryContent: MutableLiveData<String>): Fragment

}