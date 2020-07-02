package com.ksballetba.rayplus.util

import android.content.Context
import com.google.gson.Gson

//const val PROJECT_AUTHORIZATION: String = "project_authorization"
private lateinit var authorizations: List<String?>

fun setAuthorization(authorization: List<String?>) {
    authorizations = authorization
}

fun getAuthorization(): List<String?> {
    return authorizations
}

fun judgeUnlockSample(): Boolean {
    return authorizations.contains("UnlockSample")
}

