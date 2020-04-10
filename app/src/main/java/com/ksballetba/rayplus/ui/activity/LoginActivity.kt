package com.ksballetba.rayplus.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.LoginBodyBean
import com.ksballetba.rayplus.util.getLoginViewModel
import com.ksballetba.rayplus.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    companion object{
        const val TAG = "LoginActivity"
        const val SHARED_PREFERENCE_NAME = "SP_RAYPLUS"
        const val LOGIN_TOKEN = "token"
    }

    private lateinit var mViewModel:LoginViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        setContentView(R.layout.activity_login)
        initUI()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if(MainActivity.EXIT_APP_ACTION == intent?.action){
            finish()
        }
    }

    private fun initUI(){
        mViewModel = getLoginViewModel(this)
        btn_login.setOnClickListener {
            logIn()
        }
        if(getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE).contains(LOGIN_TOKEN)){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    private fun logIn(){
        val account = et_account.text.toString()
        val pwd = et_password.text.toString()
        mViewModel.login(LoginBodyBean(account,pwd)).observe(this, Observer {
            if(it.code==200){
                saveLoginToken(it.data)
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                toast("密码或账号有误，请重新输入")
            }
        })
    }

    private fun saveLoginToken(token:String){
        LogUtils.tag(TAG).d(token)
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(LOGIN_TOKEN,token)
        editor.apply()
    }
}
