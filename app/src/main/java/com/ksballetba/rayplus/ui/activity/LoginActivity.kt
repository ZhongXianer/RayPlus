package com.ksballetba.rayplus.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.loginData.LoginBodyBean
import com.ksballetba.rayplus.util.LOGIN_TOKEN
import com.ksballetba.rayplus.util.SHARED_PREFERENCE_NAME
import com.ksballetba.rayplus.util.getLoginViewModel
import com.ksballetba.rayplus.util.saveToken
import com.ksballetba.rayplus.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

/**
 * 登录Activity
 */
class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
        const val LOGIN_TYPE = "project"
        const val SYSTEM_ID = 1
    }

    private lateinit var mViewModel: LoginViewModel


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
        if (MainActivity.EXIT_APP_ACTION == intent?.action) {
            finish()
        }
    }

    /**
     * 初始化登录界面
     */
    private fun initUI() {
        mViewModel = getLoginViewModel(this)
        btn_login.setOnClickListener {
            logIn()
        }
        if (getSharedPreferences(
                SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            ).contains(LOGIN_TOKEN)
        ) {
            /*跳转到主界面，项目列表展示界面*/
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    /**
     * 登录按钮的点击事件
     *
     */
    private fun logIn() {
        val account = et_account.text.toString()
        val pwd = et_password.text.toString()
        mViewModel.login(
            LoginBodyBean(
                account,
                pwd,
                LOGIN_TYPE,
                SYSTEM_ID
            )
        )
            .observe(this, Observer {
                if (it.code == 200) {
                    saveToken(
                        this,
                        it.data.tokens,
                        it.data.userInfo.name,
                        it.data.userInfo.id,
                        it.data.userInfo.research_center_name
                    )
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    toast("密码或账号有误，请重新输入")
                }
            })
    }
}
