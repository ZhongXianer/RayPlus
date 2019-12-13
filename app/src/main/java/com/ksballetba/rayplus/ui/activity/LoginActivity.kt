package com.ksballetba.rayplus.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.ksballetba.rayplus.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        setContentView(R.layout.activity_login)
        startActivity(Intent(this,CRFActivity::class.java))
        initUI()
    }

    private fun initUI(){
        btn_login.setOnClickListener {
            logIn()
        }
    }

    private fun logIn(){
        val account = et_account.text.toString()
        val pwd = et_password.text.toString()
        if(checkAccess(account,pwd)){
            startActivity(Intent(this,MainActivity::class.java))
        }else{
            toast("密码或账号有误，请重新输入")
        }
    }

    private fun checkAccess(account:String,pwd:String):Boolean{
        return true
    }
}
