package com.ksballetba.rayplus.util

import android.content.Context
import android.content.Intent
import com.ksballetba.rayplus.data.bean.loginData.LoginResponseBean
import com.ksballetba.rayplus.ui.activity.LoginActivity
import com.lxj.xpopup.XPopup

/**
 * token、username管理类
 * 管理登录以及登录过期
 */
const val SHARED_PREFERENCE_NAME = "SP_RAYPLUS"
const val LOGIN_TOKEN: String = "token"
const val USER_NAME: String = "USER_NAME"
const val USER_ID: String = "USER_ID"
const val RESEARCH_CENTER_NAME = "RESEARCH_CENTER_NAME"
private var token: String = ""
private var mProjectId: Int = 0
private var mResearchCenterId: Int = 0

fun setToken(context: Context, projectId: Int) {
    token = "Bearer ${context.getSharedPreferences(
        SHARED_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    ).getString("${LOGIN_TOKEN}${projectId}", "")}"
    mProjectId = projectId
}

fun getToken(): String {
    return token
}

fun setResearchCenterId(researchCenterId: Int) {
    mResearchCenterId = researchCenterId
}

fun getResearchCenterId(): Int = mResearchCenterId

fun getTokenByProjectId(context: Context, projectId: Int): String {
    return "Bearer ${context.getSharedPreferences(
        SHARED_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    ).getString("${LOGIN_TOKEN}${projectId}", "")}"
}

fun getToken(context: Context, projectId: Int): String {
    return "Bearer ${context.getSharedPreferences(
        SHARED_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    ).getString("${LOGIN_TOKEN}${projectId}", "")}"
}

fun saveToken(
    context: Context,
    tokens: List<LoginResponseBean.Data.Token>,
    userName: String,
    userId: Int,
    researchCenterName: String
) {
    val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(LOGIN_TOKEN, true)
    tokens.forEach {
        editor.putString("${LOGIN_TOKEN}${it.project_id}", it.token)
    }
    editor.putString(USER_NAME, userName)
    editor.putInt(USER_ID, userId)
    editor.putString(RESEARCH_CENTER_NAME, researchCenterName)
    editor.apply()
}

fun reLogin(context: Context) {
    deleteToken(context)
    popDialog(context)
    reStartLoginActivity(context)
}

fun deleteToken(context: Context) {
    val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}

fun reStartLoginActivity(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    context.startActivity(intent)
}

fun getUserName(context: Context): String {
    val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getString(USER_NAME, "")
}

fun getUserId(context: Context): Int {
    val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getInt(USER_ID, -1)
}

fun getResearchCenterName(context: Context): String? {
    val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getString(RESEARCH_CENTER_NAME, "")
}

fun getProjectId(): Int = mProjectId

private fun popDialog(context: Context) {
    XPopup.Builder(context).asLoading("登录过期,请重新登录...").show()
}