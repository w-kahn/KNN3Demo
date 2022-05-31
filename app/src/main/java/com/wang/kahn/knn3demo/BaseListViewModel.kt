package com.wang.kahn.knn3demo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.exception.ApolloException
import com.example.rocketreserver.apolloClient
import kotlinx.coroutines.launch

class BaseListViewModel<T : Query.Data>(
    application: Application
) : AndroidViewModel(application) {

    val loading : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val empty: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val data: MutableLiveData<T> by lazy {
        MutableLiveData<T>()
    }


    public fun loadData(query:Query<T>) {
        loading.value = true
        viewModelScope.launch {
            val response = try {
                apolloClient(getApplication()).query(query).execute()
            } catch (e: ApolloException) {
                Log.e("Query", "request fail", e)
                null
            }
            data.value = response?.data

            loading.value = false

        }
    }

    fun setEmpty(isEmpty: Boolean) {
        empty.value = isEmpty
    }

}