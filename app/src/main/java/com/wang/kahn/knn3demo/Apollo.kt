package com.example.rocketreserver

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

private var instance: ApolloClient? = null

fun apolloClient(context: Context): ApolloClient {
    if (instance != null) {
        return instance!!
    }

    val okHttpClient = OkHttpClient.Builder()
        .build()

    instance = ApolloClient.Builder()
        .serverUrl("https://mw.graphql.knn3.xyz/")
        .webSocketServerUrl("wss://mw.graphql.knn3.xyz/")
        .okHttpClient(okHttpClient)
        .build()

    return instance!!
}
