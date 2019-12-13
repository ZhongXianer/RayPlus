package com.ksballetba.rayplus.network

import android.content.Context
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor(private val context:Context):Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if(originalResponse.headers("Set-Cookie").isNotEmpty()){
            val cookies = HashSet<String>()
            for (header in originalResponse.headers("Set-Cookie")){
                cookies.add(header)
            }
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putStringSet("cookie",cookies)
            editor.apply()
        }
        return originalResponse
    }
}

class AddCookiesInterceptor(private val context:Context):Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
       val builder = chain.request().newBuilder()
        val requestCookies:HashSet<String> = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("cookie",null) as HashSet<String>
        if(requestCookies.isNotEmpty()){
            for(cookie in requestCookies){
                builder.addHeader("Cookie",cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}
